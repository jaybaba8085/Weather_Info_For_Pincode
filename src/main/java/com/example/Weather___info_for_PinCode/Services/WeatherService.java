package com.example.Weather___info_for_PinCode.Services;

import com.example.Weather___info_for_PinCode.Models.WeatherInfo;
import com.example.Weather___info_for_PinCode.Models.WeatherResponse;
import com.example.Weather___info_for_PinCode.Repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private WeatherServiceAPIConnection weatherServiceAPIConnection;

    public String getWeatherInfo(String pincode, LocalDate date) throws Exception {
        WeatherResponse weatherResponse =new WeatherResponse();
        Optional<WeatherInfo> optional = weatherRepository.findByPincodeAndDate(pincode, date);
        if (optional.isPresent()) {
            //get Weather responce from weatherInfo



            return "Success";//weatherResponse;//optional.get();
        } else {
            WeatherResponse weatherResponse1 = weatherServiceAPIConnection.fetchWeatherInfoFromAPI(pincode, date);
            WeatherInfo weatherInfo =new WeatherInfo();
            //build weatherInfo





            weatherRepository.save(weatherInfo);
            return "Success";// weatherResponse1;
        }
    }
}