package org.example.homework7.mapper;

import org.example.homework7.model.WeatherApiResponse;
import org.example.homework7.model.WeatherResponse;

public class WeatherMapper {
    public static WeatherResponse toWeatherResponse(WeatherApiResponse apiResponse) {
        if (apiResponse == null || apiResponse.getMain() == null) {
            return null;
        }

        String description = "";
        String icon = "";

        if (apiResponse.getWeather() != null && !apiResponse.getWeather().isEmpty()) {
            var first = apiResponse.getWeather().getFirst();
            description = first.getDescription();
            icon = first.getIcon();
        }

        return new WeatherResponse(
                apiResponse.getName(),
                apiResponse.getMain().getTemp(),
                description,
                apiResponse.getMain().getHumidity(),
                apiResponse.getWind() != null ? apiResponse.getWind().getSpeed() : 0.0,
                icon
        );
    }
}
