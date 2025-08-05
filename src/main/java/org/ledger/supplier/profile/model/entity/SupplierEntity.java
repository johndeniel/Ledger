package org.ledger.supplier.profile.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Supplier")
public class SupplierEntity {

    @Id
    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "name", nullable = false, updatable = true)
    private String name;

    @Column(name = "address", nullable = true, updatable = true)
    private String address;

    @Column(name = "tax_id", nullable = true, updatable = true)
    private String tax_id;
   
    @Column(name = "diversity_status", nullable = true, updatable = true)
    private String diversity_status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    public SupplierEntity() {}

    public SupplierEntity(UUID uuid, String name, String address, String tax_id, String diversity_status, LocalDateTime created_at) {
        this.uuid = uuid;
        this.name = name;
        this.address = address;
        this.tax_id = tax_id;
        this.diversity_status = diversity_status;
        this.created_at = created_at;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxId() {
        return this.tax_id;
    }

    public void setTaxId(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getDiversityStatus() {
        return this.diversity_status;
    }

    public void setDiversityStatus(String diversity_status) {
        this.diversity_status = diversity_status;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
