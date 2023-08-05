package com.bancobase.bootcamp.schemas;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "currency")
public class CurrencySchema {

    @Id
    @Column(name ="currencyID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_seq")
    @SequenceGenerator(name = "currency_seq", sequenceName = "currency_seq", initialValue = 1, allocationSize = 1)
    private Long currencyID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Column(name = "value", nullable = false)
    private Double value;
}
