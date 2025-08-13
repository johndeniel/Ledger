package org.ledger.supplier.profile.unit.service;

import org.ledger.supplier.profile.model.dto.SupplierProfileRequestDTO;
import org.ledger.supplier.profile.model.dto.SupplierProfileResponseDTO;
import org.ledger.supplier.profile.model.entity.SupplierProfileEntity;
import org.ledger.supplier.profile.repository.SupplierProfileRepository;
import org.ledger.supplier.profile.service.SupplierProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SupplierProfileService Unit Tests")
class SupplierProfileServiceUnitTest {

    @Mock
    private SupplierProfileRepository supplierProfileRepository;

    @InjectMocks
    private SupplierProfileService supplierProfileService;

    private UUID supplierProfileId;
    private SupplierProfileRequestDTO supplierProfileRequest;
    private SupplierProfileEntity supplierProfileEntity;

    @BeforeEach
    void setUp() {
        supplierProfileId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        
        supplierProfileRequest = new SupplierProfileRequestDTO(
            "Tech Solutions Inc", 
            "123 Business Avenue", 
            "TAX-123456789", 
            "Minority-Owned");

        supplierProfileEntity = new SupplierProfileEntity(
            supplierProfileId, 
            "Tech Solutions Inc", 
            "123 Business Avenue", 
            "TAX-123456789", 
            "Minority-Owned", 
            now);
    }

    @Test
    @DisplayName("Should save supplier profile and return response DTO")
    void shouldSaveSupplierProfile() {
        when(supplierProfileRepository.save(any(SupplierProfileEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SupplierProfileResponseDTO result = supplierProfileService.saveSupplierProfile(supplierProfileRequest);

        assertThat(result.getUuid()).isNotNull();
        assertThat(result.getName()).isEqualTo(supplierProfileRequest.getName());
        assertThat(result.getAddress()).isEqualTo(supplierProfileRequest.getAddress());
        assertThat(result.getTaxId()).isEqualTo(supplierProfileRequest.getTaxId());
        assertThat(result.getDiversityStatus()).isEqualTo(supplierProfileRequest.getDiversityStatus());
        verify(supplierProfileRepository, times(1)).save(any(SupplierProfileEntity.class));
    }

    @Test
    @DisplayName("Should find all supplier profiles")
    void shouldFindAllSupplierProfiles() {
        when(supplierProfileRepository.findAll()).thenReturn(Arrays.asList(supplierProfileEntity));

        List<SupplierProfileResponseDTO> result = supplierProfileService.findAllSupplierProfiles();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(supplierProfileEntity.getName());
        verify(supplierProfileRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find supplier profile by ID")
    void shouldFindSupplierProfileById() {
        when(supplierProfileRepository.findById(supplierProfileId)).thenReturn(Optional.of(supplierProfileEntity));

        Optional<SupplierProfileResponseDTO> result = supplierProfileService.findSupplierProfileById(supplierProfileId);

        assertThat(result).isPresent();
        assertThat(result.get().getUuid()).isEqualTo(supplierProfileId);
        verify(supplierProfileRepository, times(1)).findById(supplierProfileId);
    }

    @Test
    @DisplayName("Should update supplier profile")
    void shouldUpdateSupplierProfile() {
        when(supplierProfileRepository.findById(supplierProfileId)).thenReturn(Optional.of(supplierProfileEntity));
        when(supplierProfileRepository.save(any(SupplierProfileEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        SupplierProfileRequestDTO updatedRequest = new SupplierProfileRequestDTO(
            "Updated Tech Solutions", 
            "456 New Street", 
            "TAX-987654321", 
            "Women-Owned");

        Optional<SupplierProfileResponseDTO> result = supplierProfileService.updateSupplierProfile(supplierProfileId, updatedRequest);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(updatedRequest.getName());
        assertThat(result.get().getAddress()).isEqualTo(updatedRequest.getAddress());
        verify(supplierProfileRepository, times(1)).findById(supplierProfileId);
        verify(supplierProfileRepository, times(1)).save(any(SupplierProfileEntity.class));
    }

    @Test
    @DisplayName("Should delete supplier profile by ID")
    void shouldDeleteSupplierProfile() {
        when(supplierProfileRepository.findById(supplierProfileId)).thenReturn(Optional.of(supplierProfileEntity));

        Optional<Boolean> result = supplierProfileService.deleteSupplierProfile(supplierProfileId);

        assertThat(result).isPresent();
        assertThat(result.get()).isTrue();
        verify(supplierProfileRepository, times(1)).findById(supplierProfileId);
        verify(supplierProfileRepository, times(1)).delete(supplierProfileEntity);
    }
}