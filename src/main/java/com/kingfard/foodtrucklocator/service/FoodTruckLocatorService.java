package com.kingfard.foodtrucklocator.service;

import com.kingfard.foodtrucklocator.DTO.FoodTruckDTO;
import com.kingfard.foodtrucklocator.DTO.FoodTruckSearchRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FoodTruckLocatorService {
    public ResponseEntity<List<FoodTruckDTO>> locateNearbyFoodTrucks(FoodTruckSearchRequest foodTruckSearchRequest);
}
