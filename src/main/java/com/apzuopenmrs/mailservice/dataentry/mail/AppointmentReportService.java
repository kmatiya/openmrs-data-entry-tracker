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

    public  HashMap<String, List<AppointmentReport>>  getUpperNenoAppointmentReport(String date){
        System.setProperty("jsse.enableSNIExtension", "false");
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("kmatiya","@1450kondwani");

        HashMap<Integer, String> getUpperNeno = locationHandler.getUpperNenoLocations();

        //HashMap<Integer, String> getUpperNeno = locationHandler.getLowerNenoLocations();

        // create request
        HttpEntity<String> request = new HttpEntity<>(headers);
        HashMap<String, List<AppointmentReport>> dailyAppointments = new HashMap<>();
        System.out.println("Collecting reports for facility.");
        try
        {
            for (Integer location:getUpperNeno.keySet()) {
                // make a request
                String locationName = getUpperNeno.get(location);
                System.out.println(locationName);
                String url = "https://neno.pih-emr.org/openmrs/ws/rest/v1/pihmalawi/data-entry?date="+date+"&location="+location;
          //      String url = "http://lisungwi.pih-emr.org:8100/openmrs/ws/rest/v1/pihmalawi/data-entry?date="+date+"&location="+location;
                ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
                ObjectMapper objectMapper = new ObjectMapper();
                List<AppointmentReport> appointmentReports = objectMapper.readValue(response.getBody(), new TypeReference<List<AppointmentReport>>(){});
                dailyAppointments.put(locationName, appointmentReports);
                System.out.println("Completion for "+locationName);
            }
            return dailyAppointments;

        }catch (HttpClientErrorException ex)
        {
            return null;
        }
        catch (ResourceAccessException ex)
        {
            return null;
        } catch (JsonMappingException e) {
            return null;
        } catch (JsonProcessingException e) {
            return null;
        }
      //  return null;
    }
}
