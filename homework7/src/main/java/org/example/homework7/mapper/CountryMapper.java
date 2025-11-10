package org.example.homework7.mapper;

import org.example.homework7.model.CountryApiResponse;
import org.example.homework7.model.CountryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CountryMapper {
    private CountryMapper() {

    }

    public static List<CountryResponse> toCountryListResponse(CountryApiResponse[] apiResponses) {
        List<CountryResponse> list = new ArrayList<>();
        if (apiResponses == null) return list;

        for (CountryApiResponse api : apiResponses) {
            CountryResponse response = new CountryResponse();
            if (api.getName() != null) {
                response.setName(api.getName().getCommon());
            }
            if (api.getCapital() != null && !api.getCapital().isEmpty()) {
                response.setCapital(api.getCapital().getFirst());
            }
            response.setPopulation(api.getPopulation());
            response.setArea(api.getArea());
            response.setRegion(api.getRegion());

            if (api.getLanguages() != null) {
                response.setLanguages(new ArrayList<>(api.getLanguages().values()));
            }

            if (api.getFlags() != null) {
                response.setFlagUrl(api.getFlags().getPng());
            }

            if (api.getCurrencies() != null) {
                List<String> currencies = api.getCurrencies().values().stream()
                        .map(currency -> currency.getName() + " (" + currency.getSymbol() + ")")
                        .collect(Collectors.toList());
                response.setCurrencies(currencies);
            }

            list.add(response);
        }
        return list;
    }
}
