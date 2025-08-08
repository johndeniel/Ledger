package org.ledger.supplier.profile.service;

import org.ledger.supplier.profile.model.dto.SupplierRequestDTO;
import org.ledger.supplier.profile.model.dto.SupplierResponseDTO;
import org.ledger.supplier.profile.model.entity.SupplierEntity;
import org.ledger.supplier.profile.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }
    
    public SupplierResponseDTO save(SupplierRequestDTO supplierRequestDTO) {
        SupplierEntity newSupplierEntity = new SupplierEntity(
            UUID.randomUUID(), 
            supplierRequestDTO.getName(), 
            supplierRequestDTO.getAddress(),
            supplierRequestDTO.getTaxId(), 
            supplierRequestDTO.getDiversityStatus(), 
            LocalDateTime.now());
        supplierRepository.save(newSupplierEntity);
        return toResponseDTO(newSupplierEntity);
    }

    public List<SupplierResponseDTO> findAllSuppliers() {
        return supplierRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    public Optional<SupplierResponseDTO> findById(UUID uuid) {
        return supplierRepository.findById(uuid)
            .map(this::toResponseDTO);
    }

    public Optional<SupplierResponseDTO> update(UUID uuid, SupplierRequestDTO supplierRequestDTO) {
        return supplierRepository.findById(uuid)
            .map(existingSupplier -> {
                existingSupplier.setName(supplierRequestDTO.getName());
                existingSupplier.setAddress(supplierRequestDTO.getAddress());
                existingSupplier.setTaxId(supplierRequestDTO.getTaxId());
                existingSupplier.setDiversityStatus(supplierRequestDTO.getDiversityStatus());
                SupplierEntity updatedSupplier = supplierRepository.save(existingSupplier);
                return toResponseDTO(updatedSupplier);
            });
    }

    public Optional<Boolean> deleteById(UUID uuid) {
        return supplierRepository.findById(uuid)
            .map(supplier -> {
                supplierRepository.delete(supplier);
                return true;
            });
    }

    private SupplierResponseDTO toResponseDTO(SupplierEntity supplier) {
        return new SupplierResponseDTO(
            supplier.getUuid(),
            supplier.getName(),
            supplier.getAddress(),
            supplier.getTaxId(),
            supplier.getDiversityStatus(),
            supplier.getCreatedAt()
        );
    }
}
