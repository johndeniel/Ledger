package org.ledger.supplier.profile.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SupplierProfileRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @Size(max = 255, message = "Tax ID must not exceed 255 characters")
    private String taxId;

    @Size(max = 255, message = "Diversity status must not exceed 255 characters")
    private String diversityStatus;


    public SupplierProfileRequestDTO(String name, String address, String taxId, String diversityStatus) {
        this.name = name;
        this.address = address;
        this.taxId = taxId;
        this.diversityStatus = diversityStatus;
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
}