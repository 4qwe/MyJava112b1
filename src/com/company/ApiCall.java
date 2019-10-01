package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection; //mit HttpsURLConnection kann ich mir java.net.HttpURLConnection sparen

public class ApiCall {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {
        ApiCall http = new ApiCall();

        System.out.println("Sending Https GET request");
        String json = http.sendGet();
        System.out.println("sendGet beendet");

        System.out.println(json);

        dasWetter einObjekt = new dasWetter("schlecht");
        System.out.println(einObjekt.weather_state_name);
        System.out.println("makeWetterJson wird ausgeführt");
        einObjekt = http.makeWetterfromJson();
        System.out.println(einObjekt.weather_state_name);

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

    private dasWetter makeWetterfromJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "{ \"weather_state_name\" : \"gut\" }";
        dasWetter einWetter = objectMapper.readValue(s, dasWetter.class);
        return einWetter;
    }

}