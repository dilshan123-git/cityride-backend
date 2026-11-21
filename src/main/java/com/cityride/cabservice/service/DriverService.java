
package com.cityride.cabservice.service;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.DriverDTO;

import java.util.List;

public interface DriverService {

    CommonResponseDto<DriverDTO> createDriver(DriverDTO dto);

    CommonResponseDto<List<DriverDTO>> getAllDrivers();

    CommonResponseDto<DriverDTO> getDriverById(Integer id);

    CommonResponseDto<DriverDTO> updateDriver(Integer id, DriverDTO dto);

    CommonResponseDto<Void> deleteDriver(Integer id);
}
