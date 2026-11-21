
package com.cityride.cabservice.service;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {

    CommonResponseDto<VehicleDTO> createVehicle(VehicleDTO vehicleDTO);

    CommonResponseDto<List<VehicleDTO>> getAllVehicles();

    CommonResponseDto<VehicleDTO> getVehicleById(Integer id);

    CommonResponseDto<VehicleDTO> updateVehicle(Integer id, VehicleDTO vehicleDTO);

    CommonResponseDto<Void> deleteVehicle(Integer id);
}
