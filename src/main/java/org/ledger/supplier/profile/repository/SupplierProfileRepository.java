package org.ledger.supplier.profile.repository;

import java.util.UUID;

import org.ledger.supplier.profile.model.entity.SupplierProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierProfileRepository extends JpaRepository <SupplierProfileEntity, UUID> {

}
