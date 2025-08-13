package org.ledger.supplier.profile.controller;

import org.ledger.supplier.profile.model.dto.SupplierProfileRequestDTO;
import org.ledger.supplier.profile.model.dto.SupplierProfileResponseDTO;
import org.ledger.supplier.profile.service.SupplierProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/suppliers/profile")
public class SupplierProfileController {

    private final SupplierProfileService supplierProfileService;

    public SupplierProfileController(SupplierProfileService supplierProfileService) {
        this.supplierProfileService = supplierProfileService;
    }

    @PostMapping
    public ResponseEntity<SupplierProfileResponseDTO> createSupplier(@Valid @RequestBody SupplierProfileRequestDTO supplierProfileRequest) {
        SupplierProfileResponseDTO createdSupplier = supplierProfileService.saveSupplierProfile(supplierProfileRequest);
        URI location = URI.create("/suppliers/profile/" + createdSupplier.getUuid());
        return ResponseEntity.created(location).body(createdSupplier);
    }

    @GetMapping
    public ResponseEntity<List<SupplierProfileResponseDTO>> getAllSuppliers() {
        List<SupplierProfileResponseDTO> suppliers = supplierProfileService.findAllSupplierProfiles();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<SupplierProfileResponseDTO> getSupplierById(@PathVariable UUID uuid) {
        SupplierProfileResponseDTO supplier = supplierProfileService.findSupplierProfileById(uuid)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Supplier not found with UUID: " + uuid));
        return ResponseEntity.ok(supplier);
    }

   
    @PutMapping("/{uuid}")
    public ResponseEntity<SupplierProfileResponseDTO> updateSupplier(
            @PathVariable UUID uuid, 
            @Valid @RequestBody SupplierProfileRequestDTO supplierProfileRequest) {
        SupplierProfileResponseDTO updatedSupplier = supplierProfileService.updateSupplierProfile(uuid, supplierProfileRequest)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Supplier not found with UUID: " + uuid));
        return ResponseEntity.ok(updatedSupplier);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable UUID uuid) {
        boolean deleted = supplierProfileService.deleteSupplierProfile(uuid).orElse(false);
        if (!deleted) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Supplier not found with UUID: " + uuid);
        }
        return ResponseEntity.noContent().build();
    }
}
