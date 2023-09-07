package com.telerikacademy.carpooling.mappers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class Map {

    private static final String API_KEY = "AjuJ7-9jHmxn-xi-VksWt8jASF05tjX8bpeWwjMDAhX6XWq6SrBMIvG877iYP_EK";

    public void main(String[] args) {
        String startLocation = "Sofia";
        String endLocation = "Plovdiv";

        try {
            JSONObject result = getDistanceAndDuration(startLocation, endLocation);
            if (result != null) {
                int distance = result.getInt("distance");
                int duration = result.getInt("duration");

                System.out.println("Distance: " + distance + " km");
                System.out.println("Duration: " + duration + " seconds");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDistance(String startLocation, String endLocation) {
        try {
            JSONObject jsonObject = getDistanceAndDuration(startLocation, endLocation);
            /*jsonObject.*/
            String string = "string";
            return string;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public JSONObject getDistanceAndDuration(String startLocation, String endLocation) throws IOException {
        String startLocationCoords = getLocationCoords(startLocation);
        String endLocationCoords = getLocationCoords(endLocation);

        String linkUrl = "http://dev.virtualearth.net/REST/v1/Routes/Driving?";
        String link = linkUrl +
                "wayPoint.1=" + URLEncoder.encode(startLocationCoords, "UTF-8") +
                "&wayPoint.2=" + URLEncoder.encode(endLocationCoords, "UTF-8") +
                "&key=" + API_KEY;

        JSONObject jsonResponse = getJSON(link);
        JSONObject result = new JSONObject();
        if (jsonResponse != null) {
            JSONArray resourceSets = jsonResponse.getJSONArray("resourceSets");
            if (resourceSets.length() > 0) {
                JSONObject resourceSet = resourceSets.getJSONObject(0);
                JSONArray resources = resourceSet.getJSONArray("resources");
                if (resources.length() > 0) {
                    JSONObject route = resources.getJSONObject(0);
                    JSONObject travelDuration = route.getJSONObject("travelDuration");
                    JSONObject travelDistance = route.getJSONObject("travelDistance");

                    int duration = travelDuration.getInt("value");
                    int distance = travelDistance.getInt("value");

                    /*JSONObject result = new JSONObject();*/
                    result.put("distance", distance);
                    result.put("duration", duration);
                    /*return result;*/
                }
            }
        }

        return result;
    }

    public JSONObject getJSON(String link) throws IOException {
        URL url = new URL(link);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            String responseJson = response.toString();
            return new JSONObject(responseJson);
        }
    }

    public String getLocationCoords(String locationName) throws IOException {
        String linkUrl = "http://dev.virtualearth.net/REST/v1/Locations/BG/";

        String address = linkUrl + locationName + "?key=" + API_KEY;

        JSONObject jsonResponse = getJSON(address);
        JSONArray resourceSets = jsonResponse.getJSONArray("resourceSets");
        double latitude = 0;
        double longitude = 0;
        if (resourceSets.length() > 0) {
            JSONObject resourceSet = resourceSets.getJSONObject(0);
            JSONArray resources = resourceSet.getJSONArray("resources");
            if (resources.length() > 0) {
                JSONObject point = resources.getJSONObject(0).getJSONObject("point");
                JSONArray coordinates = point.getJSONArray("coordinates");
                latitude = coordinates.getDouble(0);
                longitude = coordinates.getDouble(1);

                /*return latitude + "," + longitude;*/
            }
        }
        return latitude + "," + longitude;
        /*return null;*/
    }
}
