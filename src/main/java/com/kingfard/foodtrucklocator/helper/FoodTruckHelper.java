package com.kingfard.foodtrucklocator.helper;

import com.kingfard.foodtrucklocator.DTO.FoodTruckDTO;

import java.util.Map;

public interface FoodTruckHelper {
    // Populates FoodTruckDTO object with data from API and calls the calculateDistance to calculate distance.
    public FoodTruckDTO populateTruckObject(Map<String, Object> truckMap, double userLatitude, double userLongitude);

    // Calculates the distance between the user and food truck's coordinates.
    public double calculateDistance(double userLatitude, double userLongitude, double foodTruckLatitude, double foodTruckLongitude);

    }
