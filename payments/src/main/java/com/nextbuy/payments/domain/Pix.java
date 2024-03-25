package com.nextbuy.payments.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Table(name = "pix")
@Entity
@Data
public class Pix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key = UUID.randomUUID().toString();

    @JoinColumn(name = "payment_id")
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Payment payment;

}
