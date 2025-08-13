package org.ledger.supplier.profile.service;

import org.ledger.supplier.profile.model.dto.SupplierProfileRequestDTO;
import org.ledger.supplier.profile.model.dto.SupplierProfileResponseDTO;
import org.ledger.supplier.profile.model.entity.SupplierProfileEntity;
import org.ledger.supplier.profile.repository.SupplierProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SupplierProfileService {

    private final SupplierProfileRepository supplierProfileRepository;

    public SupplierProfileService(SupplierProfileRepository supplierProfileRepository) {
        this.supplierProfileRepository = supplierProfileRepository;
    }
    
    public SupplierProfileResponseDTO saveSupplierProfile(SupplierProfileRequestDTO supplierProfileRequest) {
        SupplierProfileEntity newSupplierEntity = new SupplierProfileEntity(
            UUID.randomUUID(), 
            supplierProfileRequest.getName(), 
            supplierProfileRequest.getAddress(),
            supplierProfileRequest.getTaxId(), 
            supplierProfileRequest.getDiversityStatus(), 
            LocalDateTime.now());
        supplierProfileRepository.save(newSupplierEntity);
        return toResponseDTO(newSupplierEntity);
    }

    public List<SupplierProfileResponseDTO> findAllSupplierProfiles() {
        return supplierProfileRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    public Optional<SupplierProfileResponseDTO> findSupplierProfileById(UUID uuid) {
        return supplierProfileRepository.findById(uuid)
            .map(this::toResponseDTO);
    }

    public Optional<SupplierProfileResponseDTO> updateSupplierProfile(UUID uuid, SupplierProfileRequestDTO supplierProfileRequest) {
        return supplierProfileRepository.findById(uuid)
            .map(existingSupplier -> {
                existingSupplier.setName(supplierProfileRequest.getName());
                existingSupplier.setAddress(supplierProfileRequest.getAddress());
                existingSupplier.setTaxId(supplierProfileRequest.getTaxId());
                existingSupplier.setDiversityStatus(supplierProfileRequest.getDiversityStatus());
                SupplierProfileEntity updatedSupplier = supplierProfileRepository.save(existingSupplier);
                return toResponseDTO(updatedSupplier);
            });
    }

    public Optional<Boolean> deleteSupplierProfile(UUID uuid) {
        return supplierProfileRepository.findById(uuid)
            .map(supplier -> {
                supplierProfileRepository.delete(supplier);
                return true;
            });
    }

    private SupplierProfileResponseDTO toResponseDTO(SupplierProfileEntity supplier) {
        return new SupplierProfileResponseDTO(
            supplier.getUuid(),
            supplier.getName(),
            supplier.getAddress(),
            supplier.getTaxId(),
            supplier.getDiversityStatus(),
            supplier.getCreatedAt()
        );
    }
}
