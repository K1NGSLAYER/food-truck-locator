package com.kingfard.foodtrucklocator.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodTruckDTO {
    private int objectID;
    private String applicant;
    private String facilityType;
    private String address;
    private String foodItems;
    private double distance;
}
