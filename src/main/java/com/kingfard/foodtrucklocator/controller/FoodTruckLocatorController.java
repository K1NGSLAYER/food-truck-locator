package com.kingfard.foodtrucklocator.controller;

import com.kingfard.foodtrucklocator.DTO.FoodTruckDTO;
import com.kingfard.foodtrucklocator.DTO.FoodTruckSearchRequest;
import com.kingfard.foodtrucklocator.service.FoodTruckLocatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/*
* FoodTruckLocatorController has only one GET method that takes the user's coordinates
* and radius as parameters and returns a list of Food Trucks within that radius.
* */
@RestController
@RequestMapping("/foodtruck")
public class FoodTruckLocatorController {
    private Logger logger = LoggerFactory.getLogger(FoodTruckLocatorController.class);
    private FoodTruckLocatorService foodTruckLocatorService;

    @Autowired
    public FoodTruckLocatorController(FoodTruckLocatorService foodTruckLocatorService){
       this.foodTruckLocatorService = foodTruckLocatorService;
    }
    @GetMapping("/locate")
    public ResponseEntity<List<FoodTruckDTO>> locateNearbyFoodTrucks(@RequestParam(value = "latitude", defaultValue = "37.7806943774082") double latitude,
                                                                     @RequestParam(value = "longitude", defaultValue = "-122.409668813219") double longitude,
                                                                     @RequestParam(value = "radius", defaultValue = "3.0") double radius){

        ResponseEntity<List<FoodTruckDTO>> foodTrucksResponse = null;
        FoodTruckSearchRequest foodTruckSearchRequest = new FoodTruckSearchRequest();
        foodTruckSearchRequest.setLatitude(latitude);
        foodTruckSearchRequest.setLongitude(longitude);
        foodTruckSearchRequest.setRadius(radius);

        try{
            logger.info("Calling the FoodTruckLocatorService");
            foodTrucksResponse = foodTruckLocatorService.locateNearbyFoodTrucks(foodTruckSearchRequest);
            logger.info("FoodTruckLocatorService call SUCCESSFUL");

        }catch (Exception e){
            logger.error(e.getMessage());
            foodTrucksResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.info("Call to FoodTruckLocatorService FAILED");
        }
        return foodTrucksResponse;
    }
}
