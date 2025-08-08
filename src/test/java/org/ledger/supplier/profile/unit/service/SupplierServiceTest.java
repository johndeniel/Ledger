package org.ledger.supplier.profile.unit.service;

import org.ledger.supplier.profile.model.dto.SupplierRequestDTO;
import org.ledger.supplier.profile.model.dto.SupplierResponseDTO;
import org.ledger.supplier.profile.model.entity.SupplierEntity;
import org.ledger.supplier.profile.repository.SupplierRepository;
import org.ledger.supplier.profile.service.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    private UUID supplierId;
    private SupplierRequestDTO requestDTO;
    private SupplierEntity supplierEntity;
  

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        supplierId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        
        requestDTO = new SupplierRequestDTO(
            "Test Supplier", 
            "123 Main St", 
            "TAX123", 
            "Diverse");

        supplierEntity = new SupplierEntity(
            supplierId, 
            "Test Supplier", 
            "123 Main St", 
            "TAX123", 
            "Diverse", 
            now);
    }

    @Test
    @DisplayName("Should save a new supplier and return response DTO")
    void testSave() {
        when(supplierRepository.save(any(SupplierEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SupplierResponseDTO result = supplierService.save(requestDTO);

        assertThat(result.getUuid()).isNotNull();
        assertThat(result.getName()).isEqualTo(requestDTO.getName());
        assertThat(result.getAddress()).isEqualTo(requestDTO.getAddress());
        assertThat(result.getTaxId()).isEqualTo(requestDTO.getTaxId());
        assertThat(result.getDiversityStatus()).isEqualTo(requestDTO.getDiversityStatus());
        verify(supplierRepository, times(1)).save(any(SupplierEntity.class));
    }

    @Test
    @DisplayName("Should find all suppliers and map to response DTOs")
    void testFindAllSuppliers() {
        when(supplierRepository.findAll()).thenReturn(Arrays.asList(supplierEntity));

        List<SupplierResponseDTO> result = supplierService.findAllSuppliers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(supplierEntity.getName());
        verify(supplierRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find supplier by ID and return response DTO")
    void testFindById() {
        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplierEntity));

        Optional<SupplierResponseDTO> result = supplierService.findById(supplierId);

        assertThat(result).isPresent();
        assertThat(result.get().getUuid()).isEqualTo(supplierId);
        verify(supplierRepository, times(1)).findById(supplierId);
    }

    @Test
    @DisplayName("Should update existing supplier and return response DTO")
    void testUpdate() {
        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplierEntity));
        when(supplierRepository.save(any(SupplierEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        SupplierRequestDTO updatedRequest = new SupplierRequestDTO(
            "Updated Supplier", 
            "456 New St", 
            "TAX456", 
            "Non-Diverse");

        Optional<SupplierResponseDTO> result = supplierService.update(supplierId, updatedRequest);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(updatedRequest.getName());
        assertThat(result.get().getAddress()).isEqualTo(updatedRequest.getAddress());
        verify(supplierRepository, times(1)).findById(supplierId);
        verify(supplierRepository, times(1)).save(any(SupplierEntity.class));
    }

    @Test
    @DisplayName("Should delete supplier by ID and return true")
    void testDeleteById() {
        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplierEntity));

        Optional<Boolean> result = supplierService.deleteById(supplierId);

        assertThat(result).isPresent();
        assertThat(result.get()).isTrue();
        verify(supplierRepository, times(1)).findById(supplierId);
        verify(supplierRepository, times(1)).delete(supplierEntity);
    }
}
