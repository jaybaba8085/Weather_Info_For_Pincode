package com.example.Weather___info_for_PinCode.Controllers;
import com.example.Weather___info_for_PinCode.Models.WeatherInfo;
import com.example.Weather___info_for_PinCode.Models.WeatherResponse;
import com.example.Weather___info_for_PinCode.Services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/weather_info")
public class Weather_info_for_Pincode_API {

    @Autowired
    private WeatherService weatherService;

    @GetMapping(value = "/getInfo")
    public WeatherInfo getWeatherInfo(@RequestParam String pincode, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate forDate) throws Exception {

        try{
          return  weatherService.getWeatherInfo(pincode,forDate);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Not Found");
        }
    }

}
