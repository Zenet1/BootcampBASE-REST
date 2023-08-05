package com.bancobase.bootcamp.services;

import java.util.List;
import java.util.Map;

import com.bancobase.bootcamp.dto.CurrencyDTO;
import com.bancobase.bootcamp.dto.response.ExchangeRateResponse;
import com.bancobase.bootcamp.dto.response.Symbol;
import com.bancobase.bootcamp.dto.response.SymbolsNameResponse;
import com.bancobase.bootcamp.repositories.CurrencyRepository;
import com.bancobase.bootcamp.schemas.CurrencySchema;

import org.hibernate.mapping.Array;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancobase.bootcamp.dto.response.ExchangeRateResponse;
import com.bancobase.bootcamp.dto.response.Symbol;
import com.bancobase.bootcamp.dto.response.SymbolsNameResponse;
import com.bancobase.bootcamp.http.APIExchangeRateClient;

@RestController
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        setCurrencies();
    }

    @GetMapping("/currency")
    List<CurrencyDTO> getCurrencies() throws Exception{
        return currencyRepository
                .findAll()
                .stream()
                .map(CurrencyDTO::getFromSchema)
                .toList();
    }

    private void setCurrencies(){
        
        APIExchangeRateClient API = new APIExchangeRateClient();
        ExchangeRateResponse exchangeRateResponse = API.getExchangeRate();
        SymbolsNameResponse symbolsNameResponse = API.getSymbolsName();

        Map<String, Double> exchangeRates = exchangeRateResponse.getRates();

        for(String key : symbolsNameResponse.getSymbols().keySet()){
            Symbol symbol = symbolsNameResponse.getSymbols().get(key);
            String name = symbol.getDescription();
            String code = symbol.getCode();
            Double value = exchangeRates.get(code);

            if (name != null && code != null && value != null) {
                CurrencySchema currencySchema = new CurrencySchema();
                currencySchema.setName(name);
                currencySchema.setSymbol(code);
                currencySchema.setValue(value);
                currencyRepository.save(currencySchema);
            }
        }
    }


}
