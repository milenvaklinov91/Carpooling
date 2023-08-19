/*
package com.telerikacademy.carpooling.mappers;

public class MapDistance {

    String startLocation = "Sofia";
    String endLocation = "Plovdiv";

    String time = getDurationInSec(startLocation,endLocation);
    String distance = getDistanceString(startLocation,endLocation);

    System.out.println(time);
    System.out.println(distance);



    public static JSONObject getJSONResponseFromLink(String link) {
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

    public static String getLocationCoords (String LocationName) {
        String apikey = "AjuJ7-9jHmxn-xi-VksWt8jASF05tjX8bpeWwjMDAhX6XWq6SrBMIvG877iYP_EK";
        String apiurl = http://dev.virtualearth.net/REST/v1/Locations/BG/;

        String link = apiUrl + LocationName "?key=" + apikey;

        JSONObject jsonResponse = getJSONResponseFromLink(link);

        JSONArray coordsJSON = jsonResponse
                .getJSONArray(key:"resourceSets").getJSONObject(index:0)
                .getJSONArray(key:"resources").getJSONObject(index:0)
                .getJSONArray(key:"result").getJSONObject(index:0)

        return results;
    }

    public static String getDistanceString(String startLocation, String endLocation) {
        int distance = getDistanceInKm(startLocation, endLocation);
        return distance + " km";
    }

    public static int getDurationInSec(String startLocation, String endLocation) {
        JSONObject results = getResultsFromLocationNames(startLocation, endLocation);
        int seconds results.getInt(key:"travelDuration");

        return seconds;
    }

    public static int getDistanceInKm(String startLocation, String endLocation) {
        JSONObject results = getResultsFromLocationNames(startLocation, endLocation);
        int distance = results.getInt(key:"travelDistance");
    }

    public static JSONObject getResultsFromLocationNames(String startLocation, String endLocation) {
        String startLocationCoords = getLocationCoords(startLocation);
        String endLocationCoords = getLocationCoords(endLocation);

        String apikey = "AjuJ7-9jHmxn-xi-VksWt8jASF05tjX8bpeWwjMDAhX6XWq6SrBMIvG877iYP_EK";
        String apiurl = "http://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix";

        JSONObject results;
        String link = "";
        try {
            link = apiurl + "?origins=" + URLEncoder.encode(startLocationCoords, enc:"UTF-8")
            +"&destinations=" + URLEncoder.encode(endLocationCoords, enc:"UTF-8")
            +"&timeUnit-second&travelMode=driving&key=" + apikey;
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonResponse = getJSONResponseFromLink(link);
        results = jsonResponse
                .getJSONArray(key:"resourceSets").getJSONObject(index:0)
                .getJSONArray(key:"resources").getJSONObject(index:0)
                .getJSONArray(key:"result").getJSONObject(index:0)

        return results;
    }
}*/
