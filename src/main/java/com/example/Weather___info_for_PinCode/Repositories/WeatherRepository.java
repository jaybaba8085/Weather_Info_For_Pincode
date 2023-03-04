package com.example.Weather___info_for_PinCode.Repositories;

import com.example.Weather___info_for_PinCode.Models.WeatherInfo;
import com.example.Weather___info_for_PinCode.Models.WeatherResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherInfo, Long> {

    Optional<WeatherInfo> findByPincodeAndDate(String pincode, LocalDate date);
}