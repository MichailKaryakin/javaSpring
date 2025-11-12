package org.example.homework7.mapper;

import org.example.homework7.dto.UserListResponse;
import org.example.homework7.dto.UserResponse;
import org.example.homework7.model.RandomUserApiResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMapper {

    /**
     * Конвертирует RandomUserApiResponse в UserListResponse
     *
     * @param apiResponse ответ RandomUser API
     * @param filters примененные фильтры (нет фильтров - null)
     * @return объект UserListResponse
     */
    public static UserListResponse fromApiResponse(RandomUserApiResponse apiResponse, Map<String, String> filters) {
        UserListResponse response = new UserListResponse();

        if (apiResponse == null || apiResponse.getResults() == null) {
            response.setUsers(List.of());
            response.setTotalResults(0);
            response.setFilters(filters);
            return response;
        }

        List<UserResponse> users = apiResponse.getResults().stream()
                .map(UserMapper::mapToUserResponse)
                .collect(Collectors.toList());

        response.setUsers(users);
        response.setTotalResults(users.size());
        response.setFilters(filters);

        return response;
    }

    /**
     * Конвертирует одного юзера из RandomUserApiResponse в UserResponse
     */
    private static UserResponse mapToUserResponse(RandomUserApiResponse.User user) {
        if (user == null) return null;

        UserResponse dto = new UserResponse();

        dto.setId(user.getLogin() != null ? user.getLogin().getUuid() : null);
        dto.setGender(user.getGender());
        dto.setFullName(user.getName() != null
                ? user.getName().getFirst() + " " + user.getName().getLast()
                : null);
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getLogin() != null ? user.getLogin().getUsername() : null);
        dto.setAge(user.getDob() != null ? user.getDob().getAge() : 0);
        dto.setPhone(user.getPhone());
        dto.setCountry(user.getLocation() != null ? user.getLocation().getCountry() : null);
        dto.setCity(user.getLocation() != null ? user.getLocation().getCity() : null);
        dto.setPictureUrl(user.getPicture() != null ? user.getPicture().getMedium() : null);
        dto.setNationality(user.getNat());

        return dto;
    }
}
