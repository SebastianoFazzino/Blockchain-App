package com.blockchain.app.models.dtos;

import com.blockchain.app.models.BaseModel;
import com.blockchain.app.models.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransferRequest extends BaseModel {

    private BigDecimal amount;

    private String receivingAddress;

    private Currency currency;

    private Date requestDate = new Date();

    private boolean processed = false;

    private Date processedAt;


    public TransferRequest(
            Currency currency,
            BigDecimal amount,
            String receivingAddress
    ) {
        this.currency = currency;
        this.amount = amount;
        this.receivingAddress = receivingAddress;
    }

}
