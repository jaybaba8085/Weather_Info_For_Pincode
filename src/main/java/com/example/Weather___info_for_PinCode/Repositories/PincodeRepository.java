package com.example.Weather___info_for_PinCode.Repositories;

import com.example.Weather___info_for_PinCode.Models.PincodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PincodeRepository extends JpaRepository<PincodeInfo,Integer>
{
    PincodeInfo findByPincode(String pincode);
}
