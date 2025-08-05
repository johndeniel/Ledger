package org.ledger.supplier.profile.repository;

import java.util.UUID;

import org.ledger.supplier.profile.model.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository <SupplierEntity, UUID> {

}
