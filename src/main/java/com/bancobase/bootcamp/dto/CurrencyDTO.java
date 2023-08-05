package com.bancobase.bootcamp.dto;

import com.bancobase.bootcamp.schemas.CurrencySchema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CurrencyDTO {
    private String name;
    private String symbol;
    private Double value;

    public static CurrencyDTO getFromSchema(CurrencySchema currencySchema) {
        return CurrencyDTO
                .builder()
                .name(currencySchema.getName())
                .symbol(currencySchema.getSymbol())
                .value(currencySchema.getValue())
                .build();
    }
}