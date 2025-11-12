package org.example.homework7.dto;

import java.util.List;
import java.util.Map;

public class UserListResponse {
    private List<UserResponse> users;
    private int totalResults;
    private Map<String, String> filters;

    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }
}
