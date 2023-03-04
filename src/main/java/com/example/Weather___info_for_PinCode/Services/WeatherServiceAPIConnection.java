package com.example.Weather___info_for_PinCode.Services;

import com.example.Weather___info_for_PinCode.Models.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

@Service
public class WeatherServiceAPIConnection {

    private static final String API_KEY = "1cd484c55ed6a26693398bcae22720c1";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse fetchWeatherInfoFromAPI(String pincode, LocalDate date)
    {
        String formattedDate = date.format(DATE_FORMATTER);
        String url = String.format("%s?zip=%s,IN&appid=%s&dt=%s", BASE_URL, pincode, API_KEY, formattedDate);
        return restTemplate.getForObject(url, WeatherResponse.class);
    }

}
