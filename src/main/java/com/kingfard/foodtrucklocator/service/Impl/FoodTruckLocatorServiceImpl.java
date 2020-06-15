package com.kingfard.foodtrucklocator.service.Impl;

import com.kingfard.foodtrucklocator.DTO.FoodTruckDTO;
import com.kingfard.foodtrucklocator.DTO.FoodTruckSearchRequest;
import com.kingfard.foodtrucklocator.helper.FoodTruckHelper;
import com.kingfard.foodtrucklocator.service.FoodTruckLocatorService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class FoodTruckLocatorServiceImpl implements FoodTruckLocatorService {
    private FoodTruckHelper helper;
    private Logger logger = LoggerFactory.getLogger(FoodTruckLocatorServiceImpl.class);

    @Autowired
    public FoodTruckLocatorServiceImpl (FoodTruckHelper helper){
        this.helper = helper;
    }
    // API Endpoint for SF Food Truck Data
    String uri = "https://data.sfgov.org/resource/rqzj-sfat.json";

    @Override
    public ResponseEntity<List<FoodTruckDTO>> locateNearbyFoodTrucks(FoodTruckSearchRequest foodTruckSearchRequest) {
        ResponseEntity<List<FoodTruckDTO>> foodTrucksResponse = null;
        try{
            RestTemplate restTemplate = new RestTemplate();

            String response = restTemplate.getForObject(uri, String.class);

            List<FoodTruckDTO> foodTrucks = new ArrayList<>();

            // Converts RestTemplate string response to JSONArray
            JSONArray foodTrucksArray = new JSONArray(response);

           /* Iterates through the JSONArray and populates the foodTruck Array
            * with FoodTruckDTO Objects
            */
            int totalFoodTrucks = foodTrucksArray.length();
            for (int i = 0; i < totalFoodTrucks; i++){
                JSONObject foodTruck = foodTrucksArray.getJSONObject(i);
                Map<String, Object> truck = foodTruck.toMap();
                FoodTruckDTO foodTruckDTO = helper.populateTruckObject(truck,
                        foodTruckSearchRequest.getLatitude(),
                        foodTruckSearchRequest.getLongitude());

                // Only adds Trucks within the requested radius
                if (foodTruckDTO.getFacilityType() != null &&
                        foodTruckDTO.getFacilityType().equalsIgnoreCase("Truck") &&
                        foodTruckDTO.getDistance() <= foodTruckSearchRequest.getRadius()){

                    foodTrucks.add(foodTruckDTO);
                }
            }
            foodTrucks.sort(Comparator.comparing(FoodTruckDTO::getDistance));

            //Checks if requested limit is greater than the size of list of foodTrucks
            if (foodTruckSearchRequest.getLimit() < foodTrucks.size()){
                foodTrucks = foodTrucks.subList(0,foodTruckSearchRequest.getLimit());
            }
                foodTrucksResponse = new ResponseEntity<>(foodTrucks, HttpStatus.OK);
        } catch (JSONException e) {
           logger.error(e.getMessage());
            foodTrucksResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return foodTrucksResponse;
    }
}
