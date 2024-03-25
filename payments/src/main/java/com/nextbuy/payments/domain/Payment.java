package com.nextbuy.payments.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "payment")
@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCarrinho;

    private Date paymentDay = new Date();

    private BigDecimal totalValue;

    @OneToOne(mappedBy = "payment", orphanRemoval = true, cascade = CascadeType.ALL)
    private Pix pix;

    @OneToOne(mappedBy = "payment", orphanRemoval = true, cascade = CascadeType.ALL)
    private Card card;
}
