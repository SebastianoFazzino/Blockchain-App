package com.blockchain.app.repositories;

import com.blockchain.app.entities.TransferRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransferRequestRepository extends JpaRepository<TransferRequestEntity, UUID> {

    List<TransferRequestEntity> findByProcessedIsFalse();

    List<TransferRequestEntity> findByReceivingAddress(String receivingAddress);

    List<TransferRequestEntity> findByRequestDate(Date requestDate);

}
