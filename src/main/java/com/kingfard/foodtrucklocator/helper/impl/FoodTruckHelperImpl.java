package com.kingfard.foodtrucklocator.helper.impl;

import com.kingfard.foodtrucklocator.DTO.FoodTruckDTO;
import com.kingfard.foodtrucklocator.helper.FoodTruckHelper;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class FoodTruckHelperImpl implements FoodTruckHelper {
    @Override
    public FoodTruckDTO populateTruckObject(Map<String, Object> truckMap, double userLatitude, double userLongitude) {
        FoodTruckDTO foodTruckDTO = new FoodTruckDTO();
        foodTruckDTO.setObjectID(Integer.parseInt(truckMap.get("objectid").toString()));
        foodTruckDTO.setApplicant((String)truckMap.get("applicant"));
        /*
         * Some truck objects from the API have no facilitytype and fooditems field.
         * The following if/else blocks checks for those null fields to avoid NullPointer Errors.
         * */
        if (truckMap.get("facilitytype") == null){
            foodTruckDTO.setFacilityType(null);
        }else{
            foodTruckDTO.setFacilityType(truckMap.get("facilitytype").toString());
        }
        if (truckMap.get("fooditems") == null){
            foodTruckDTO.setFoodItems(null);
        }else{
            foodTruckDTO.setFoodItems((truckMap.get("fooditems").toString()));
        }

        foodTruckDTO.setAddress((truckMap.get("address").toString()));
        double foodTruckLatitude = Double.parseDouble(truckMap.get("latitude").toString());
        double foodTruckLongitude = Double.parseDouble(truckMap.get("longitude").toString());

        FoodTruckHelper helper = new FoodTruckHelperImpl();
        double distance = helper.calculateDistance(userLatitude,userLongitude,foodTruckLatitude,foodTruckLongitude);

        foodTruckDTO.setDistance(distance);
        return foodTruckDTO;
    }

    /*
     * Given the latitude and longitude of two coordinates as parameters,
     * calculateDistance computes and returns the distance between coordinates.
     * Default unit is Miles.
     *  */
    @Override
    public double calculateDistance(double userLatitude, double userLongitude, double foodTruckLatitude, double foodTruckLongitude) {
        double distance;
        if ((userLatitude == foodTruckLatitude) && (userLongitude == foodTruckLongitude)){
            return 0;
        }else{
            double theta = userLongitude - foodTruckLongitude;
            distance = Math.sin(Math.toRadians(userLatitude)) * Math.sin(Math.toRadians(foodTruckLatitude)) +
                    Math.cos(Math.toRadians(userLatitude)) * Math.cos(Math.toRadians(foodTruckLatitude)) * Math.cos(Math.toRadians(theta));
            distance = Math.acos(distance);
            distance = Math.toDegrees(distance);
            distance = distance * 60 * 1.1515;
        }
        return distance;
    }
}
