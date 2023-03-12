package com.blockchain.app.controllers;

import com.blockchain.app.models.dtos.TransferRequest;
import com.blockchain.app.models.enums.Currency;
import com.blockchain.app.services.TransferRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/transfer-request"})
public class TransferRequestController {

    private final TransferRequestService transferRequestService;


    @PostMapping(value = "/new")
    public ResponseEntity<TransferRequest> save(
            @RequestParam Currency currency,
            @RequestParam BigDecimal amount,
            @RequestParam String address
    ) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/transfer-request/new").toUriString());

        return ResponseEntity.created(uri).body(transferRequestService
                .createNewTransferRequest(currency, amount, address));
    }

    @GetMapping("/to-pay")
    public ResponseEntity<List<TransferRequest>> getRequestToPay() {
        return ResponseEntity.ok(transferRequestService.getRequestToPay());
    }
}
