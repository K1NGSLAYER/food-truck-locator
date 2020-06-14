package com.kingfard.foodtrucklocator.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodTruckSearchRequest {
    private double latitude;
    private double longitude;
    private double radius;
    private int limit;
}
