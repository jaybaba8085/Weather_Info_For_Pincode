package com.example.Weather___info_for_PinCode.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherResponse {

    @JsonProperty("main")
    private MainWeather mainWeather;


    public MainWeather getMainWeather() {
        return mainWeather;
    }

    public void setMainWeather(MainWeather mainWeather) {
        this.mainWeather = mainWeather;
    }
    public class MainWeather {

        @JsonProperty("temp")
        private double temperature;

        @JsonProperty("humidity")
        private int humidity;

        public String getTemperature() {
            return String.format("%.0f",(temperature- 274.15));
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }
    }
}