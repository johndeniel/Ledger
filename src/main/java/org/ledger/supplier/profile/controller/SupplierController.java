package org.ledger.supplier.profile.controller;

import org.ledger.supplier.profile.model.dto.SupplierRequestDTO;
import org.ledger.supplier.profile.model.dto.SupplierResponseDTO;
import org.ledger.supplier.profile.service.SupplierService;
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
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(@Valid @RequestBody SupplierRequestDTO supplierRequestDTO) {
        SupplierResponseDTO createdSupplier = supplierService.save(supplierRequestDTO);
        URI location = URI.create("/suppliers/profile/" + createdSupplier.getUuid());
        return ResponseEntity.created(location).body(createdSupplier);
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers() {
        List<SupplierResponseDTO> suppliers = supplierService.findAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable UUID uuid) {
        SupplierResponseDTO supplier = supplierService.findById(uuid)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Supplier not found with UUID: " + uuid));
        return ResponseEntity.ok(supplier);
    }

   
    @PutMapping("/{uuid}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(
            @PathVariable UUID uuid, 
            @Valid @RequestBody SupplierRequestDTO supplierRequestDTO) {
        SupplierResponseDTO updatedSupplier = supplierService.update(uuid, supplierRequestDTO)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Supplier not found with UUID: " + uuid));
        return ResponseEntity.ok(updatedSupplier);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable UUID uuid) {
        boolean deleted = supplierService.deleteById(uuid).orElse(false);
        if (!deleted) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Supplier not found with UUID: " + uuid);
        }
        return ResponseEntity.noContent().build();
    }
}
