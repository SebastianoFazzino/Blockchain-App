package com.blockchain.app.entities;

import com.blockchain.app.models.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "transfer_request",
        indexes = {
                @Index(name = "currency_idx", columnList = "currency"),
                @Index(name = "receiving_address_idx", columnList = "receivingAddress"),
                @Index(name = "request_date_idx", columnList = "requestDate")
        }
)
public class TransferRequestEntity extends BaseEntity {

        @Column(precision = 19, scale = 16)
        private BigDecimal amount;

        private String receivingAddress;

        @Enumerated(EnumType.STRING)
        private Currency currency;

        @Temporal(TemporalType.DATE)
        private Date requestDate;

        private boolean processed;

        @Temporal(TemporalType.TIMESTAMP)
        private Date processedAt;

}
