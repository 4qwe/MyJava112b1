package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import com.company.api.WeatherApiResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection; //mit HttpsURLConnection kann ich mir java.net.HttpURLConnection sparen

public class ApiCall {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {
        ApiCall http = new ApiCall();

        System.out.println("Sending Https GET request");
        String json = http.sendGet();
        WeatherApiResponse eineResponse; //JSON String
        eineResponse = http.makeResponsefromJson(json); // was will ich aus dem ersten Durchgang der object mapper methode für ein ergebnis haben?
        System.out.println("Vorhersage für morgen ist " + eineResponse.consolidated_weather[1].weather_state_name);

    }

    private String sendGet() throws Exception {
        String url = "https://www.metaweather.com/api/location/656958/";

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String s = response.toString();
        return s;
    }

    private WeatherApiResponse makeResponsefromJson(String s) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        WeatherApiResponse gemapped = objectMapper.readValue(s, WeatherApiResponse.class);
        return gemapped;
    }

}