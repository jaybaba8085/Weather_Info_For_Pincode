package com.example.Weather___info_for_PinCode.Services;

import com.example.Weather___info_for_PinCode.Models.PincodeInfo;
import com.example.Weather___info_for_PinCode.Models.WeatherInfo;
import com.example.Weather___info_for_PinCode.Models.WeatherResponse;
import com.example.Weather___info_for_PinCode.Repositories.PincodeRepository;
import com.example.Weather___info_for_PinCode.Repositories.WeatherRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class WeatherService {
    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private PincodeRepository pincodeRepository;
    @Autowired
    private RestTemplate restTemplate;

    private static final String API_KEY = "ee8a80e6ec2a76e60bf1ce5b2e146d21";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String GEOCODING_API_KEY = "ee8a80e6ec2a76e60bf1ce5b2e146d21";
    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";

    public WeatherInfo getWeatherInfo(String pincode, LocalDate date)
    {
        PincodeInfo pincodeInfo = pincodeRepository.findByPincode(pincode);
        if (pincodeInfo == null)
        {
            String geocodingUrl = GEOCODING_API_URL + pincode + "&key=" + GEOCODING_API_KEY;

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(geocodingUrl, String.class);
            String response = responseEntity.getBody();
            double lat = 0, lon = 0;

            // Extract latitude and longitude from the response JSON
            JsonObject responseJson = JsonParser.parseString(response).getAsJsonObject();
            JsonArray resultsArray = responseJson.getAsJsonArray("results");
            JsonObject results = resultsArray.get(0).getAsJsonObject();
            JsonObject geometry = results.getAsJsonObject("geometry");
            JsonObject location = geometry.getAsJsonObject("location");
             lat = location.get("lat").getAsDouble();
             lon = location.get("lng").getAsDouble();

            pincodeInfo.setLatitude(lat);
            pincodeInfo.setLongitude(lon);

            pincodeRepository.save(pincodeInfo);
        }

        Optional<WeatherInfo> weatherInfo = weatherRepository.findByPincodeAndDate(pincode, date);
        if (weatherInfo.isPresent())
        {
            return weatherInfo.get();
        } else {

            String url = buildUrl(pincodeInfo.getLatitude(), pincodeInfo.getLongitude(), date);

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

            String response = responseEntity.getBody();

            WeatherInfo newWeatherInfo = parseResponse(response, pincode, date);

            weatherRepository.save(newWeatherInfo);

            return newWeatherInfo;
        }
    }

    private String buildUrl(double lat, double lon, LocalDate date) {
        String url = String.format("%s?lat=%s&lon=%s&units=metric&dt=%s&appid=%s",
                BASE_URL, lat, lon, date.atStartOfDay().toEpochSecond(ZoneOffset.UTC), API_KEY);
        return url;
    }

    private WeatherInfo parseResponse(String response, String pincode, LocalDate date) {
        WeatherInfo weatherInfo = WeatherInfo.builder().response(response).pincode(pincode).date(date).build();
        return weatherInfo;
    }

    }
