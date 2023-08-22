
package com.telerikacademy.carpooling.mappers;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MapDistance {

    String startLocation = "Sofia";
    String endLocation = "Plovdiv";

   /* String time = getDuration(startLocation, endLocation);
    String distance = getDistance(startLocation, endLocation);*/

    public static JSONObject getJSON(String link) {
        JSONObject jsonResponse = new JSONObject();
        try {
            URL url = new URL(link);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String responseJson = response.toString();
            jsonResponse = new JSONObject(responseJson);

            String city = "sofia";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }


    public static String getLocation(String LocationName) throws IOException {
        String apikey = "AjuJ7-9jHmxn-xi-VksWt8jASF05tjX8bpeWwjMDAhX6XWq6SrBMIvG877iYP_EK";
        String linkUrl = "http://dev.virtualearth.net/REST/v1/Locations/BG/";

        String address = linkUrl + LocationName + "?key=" + apikey;

        URL url = new URL(address);

        HttpURLConnection huc = (HttpURLConnection) url.openConnection();

        huc.setRequestMethod("GET");

        System.out.println(huc.getInputStream());



         JSONObject jsonResponse = getJSON(address);

        /*/JSONArray coordsJSON = jsonResponse
                .getJSONArray(key:"resourceSets").getJSONObject(index:0)
                .getJSONArray(key:"resources").getJSONObject(index:0)
                .getJSONArray(key:"result").getJSONObject(index:0)*/

        return null;
    }



    /*public static String getDistanceString(String startLocation, String endLocation) {
        int distance = getDistanceInKm(startLocation, endLocation);
        return distance + " km";
    }*/

    /*public static int getDurationInSec(String startLocation, String endLocation) {
        JSONObject results = getResults(startLocation, endLocation);
        int seconds = results.getInt(key:"travelDuration");

        return seconds;
    }

    public static int getDistanceInKm(String startLocation, String endLocation) {
        JSONObject results = getResults(startLocation, endLocation);
        int distance = results.getInt(key:"travelDistance");
    }*/

    public static JSONObject getResults(String startLocation, String endLocation) {
        /*String startLocationCoords = getLocationCoords(startLocation);
        String endLocationCoords = getLocationCoords(endLocation);*/

        String apikey = "AjuJ7-9jHmxn-xi-VksWt8jASF05tjX8bpeWwjMDAhX6XWq6SrBMIvG877iYP_EK";
        String linkUrl = "http://dev.virtualearth.net/REST/v1/Locations/BG/";

        JSONObject results;
        String link = "";
        /*try {
            link = linkUrl + "?origins=" + URLEncoder.encode(startLocationCoords, enc:"UTF-8")
            + "&destinations=" + URLEncoder.encode(endLocationCoords, enc:"UTF-8")
            + "&timeUnit-second&travelMode=driving&key=" + apikey;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return results;
    }*/

    /*private static String getLocationCoords(String startLocation) {
    }*/
        return null;
    }
}
