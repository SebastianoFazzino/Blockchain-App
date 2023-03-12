package com.blockchain.app.services;

import com.blockchain.app.entities.TransferRequestEntity;
import com.blockchain.app.mappers.TransferRequestMapper;
import com.blockchain.app.models.dtos.TransferRequest;
import com.blockchain.app.models.enums.Currency;
import com.blockchain.app.repositories.TransferRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferRequestService {

    private final TransferRequestMapper mapper;
    private final TransferRequestRepository repository;
    private final UtilsService utilsService;

    //*************************************************
    // CRUD Functions

    /**
     * Save a TransferRequest
     * @param transferRequest TransferRequest
     * @return TransferRequest
     */
    public TransferRequest save(TransferRequest transferRequest) {

        log.info("Saving {} TransferRequest {}", transferRequest.getId() == null ? "new" : "", transferRequest);
        TransferRequestEntity entity = repository.save(mapper.convertToEntity(transferRequest));
        return mapper.convertToDto(entity);
    }

    /**
     * Create a new TransferRequest
     * @param currency Currency
     * @param amount BigDecimal
     * @param receivingAddress String
     * @return TransferRequest
     */
    public TransferRequest createNewTransferRequest(
            Currency currency, BigDecimal amount, String receivingAddress) {

        log.info("Creating new TransferRequest with amount {} {} and receiving address {}",
                amount, currency, receivingAddress);

        TransferRequest transferRequest = new TransferRequest(
                currency, amount, receivingAddress);

        return this.save(transferRequest);
    }


    /**
     * Get TransferRequests to pay
     * @return List<TransferRequest>
     */
    public List<TransferRequest> getRequestToPay() {

        log.info("Getting TransferRequests to pay");
        return repository.findByProcessedIsFalse()
                .stream().map(mapper::convertToDto).collect(Collectors.toList());
    }

    /**
     * Get TransferRequests by receiving address
     * @param receivingAddress String
     * @return List<TransferRequest>
     */
    public List<TransferRequest> getByReceivingAddress(String receivingAddress) {

        log.info("Getting TransferRequests by receiving address {}", receivingAddress);
        return repository.findByReceivingAddress(receivingAddress)
                .stream().map(mapper::convertToDto).collect(Collectors.toList());
    }

    /**
     * Get TransferRequests by request date
     * @param requestDate Date
     * @return List<TransferRequest>
     */
    public List<TransferRequest> getByRequestDate(Date requestDate) {

        log.info("Getting TransferRequests by request date {}", utilsService.formatDate(requestDate));
        return repository.findByRequestDate(requestDate)
                .stream().map(mapper::convertToDto).collect(Collectors.toList());
    }
}
