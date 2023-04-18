package com.example.fypproject;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.google.android.gms.maps.model.LatLng;

public class DistanceCalculator {
    private static final int EARTH_RADIUS = 6371;


    /**
     * Use Great Circle distance formula to calculate distance between 2 coordinates in meters.
     */
    public double greatCircleInFeet(LatLng latLng1, LatLng latLng2) {
        return distanceInKm(latLng1.latitude, latLng1.longitude, latLng2.latitude,
                latLng2.longitude) * 3280.84;
    }

    /**
     * Use Great Circle distance formula to calculate distance between 2 coordinates in meters.
     */
    public double greatCircleInMeters(LatLng latLng1, LatLng latLng2) {
        return distanceInKm(latLng1.latitude, latLng1.longitude, latLng2.latitude,
                latLng2.longitude) * 1000;
    }

    /**
     * Use Great Circle distance formula to calculate distance between 2 coordinates in kilometers.
     * https://software.intel.com/en-us/blogs/2012/11/25/calculating-geographic-distances-in-location-aware-apps
     */
    public double distanceInKm(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371.01; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1 - lon2));
    }
}
