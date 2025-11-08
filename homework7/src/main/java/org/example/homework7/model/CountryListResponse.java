package org.example.homework7.model;

import java.util.List;

public class CountryListResponse {
    private List<CountryResponse> countries;
    private int totalResults;
    private String filterApplied;

    public List<CountryResponse> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryResponse> countries) {
        this.countries = countries;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getFilterApplied() {
        return filterApplied;
    }

    public void setFilterApplied(String filterApplied) {
        this.filterApplied = filterApplied;
    }
}
