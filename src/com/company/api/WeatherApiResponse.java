package com.company.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {
    public DayForecast[] consolidated_weather;
}
