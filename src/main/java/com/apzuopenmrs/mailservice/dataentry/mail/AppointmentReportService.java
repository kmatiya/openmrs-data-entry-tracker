package com.apzuopenmrs.mailservice.dataentry.mail;

import com.apzuopenmrs.mailservice.dataentry.model.AppointmentReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class AppointmentReportService {

    @Autowired
    LocationHandler locationHandler;

    public  HashMap<String, List<AppointmentReport>> getAppointmentReport(String date, String username, String password,String serverLocation){
        System.setProperty("jsse.enableSNIExtension", "false");
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username,password);
        HashMap<Integer, String> getLocations;

        String url = "";
        if(serverLocation.equals("upper")){
            getLocations = locationHandler.getUpperNenoLocations();
            url = "https://neno.pih-emr.org/openmrs/ws/rest/v1/pihmalawi/data-entry?date="+date+"&location=";
        }
        else {
            getLocations = locationHandler.getLowerNenoLocations();
            url = "http://lisungwi.pih-emr.org:8100/openmrs/ws/rest/v1/pihmalawi/data-entry?date="+date+"&location=";
        }

        // create request
        HttpEntity<String> request = new HttpEntity<>(headers);
        HashMap<String, List<AppointmentReport>> dailyAppointments = new HashMap<>();
        System.out.println("Collecting reports for facility.");
        try
        {
            for (Integer location:getLocations.keySet()) {
                // make a request
                String locationName = getLocations.get(location);
                System.out.println(locationName);
                String completeUrl = url+location;
                ResponseEntity<String> response = new RestTemplate().exchange(completeUrl, HttpMethod.GET, request, String.class);
                ObjectMapper objectMapper = new ObjectMapper();
                List<AppointmentReport> appointmentReports = objectMapper.readValue(response.getBody(), new TypeReference<List<AppointmentReport>>(){});
                dailyAppointments.put(locationName, appointmentReports);
                System.out.println("Completion for "+locationName);
            }
            return dailyAppointments;

        }catch (HttpClientErrorException ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }
        catch (ResourceAccessException ex)
        {
            System.out.println(ex.getMessage());
            return null;
        } catch (JsonMappingException ex) {
            System.out.println(ex.getMessage());
            return null;
        } catch (JsonProcessingException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }

      //  return null;
    }
}
