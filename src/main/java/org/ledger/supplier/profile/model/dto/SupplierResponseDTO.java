package org.ledger.supplier.profile.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class SupplierResponseDTO {

    private UUID uuid;
    private String name;
    private String address;
    private String taxId;
    private String diversityStatus;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public SupplierResponseDTO(UUID uuid, String name, String address, String taxId, String diversityStatus, LocalDateTime createdAt) {
        this.uuid = uuid;
        this.name = name;
        this.address = address;
        this.taxId = taxId;
        this.diversityStatus = diversityStatus;
        this.createdAt = createdAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getDiversityStatus() {
        return diversityStatus;
    }

    public void setDiversityStatus(String diversityStatus) {
        this.diversityStatus = diversityStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}