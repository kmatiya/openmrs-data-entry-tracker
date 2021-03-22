package com.apzuopenmrs.mailservice.dataentry.mail;

import com.apzuopenmrs.mailservice.dataentry.model.AppointmentReport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentEmailGenerator {
    public String generateAppointmentEmail(List<FacilityCount> facilityCountList, String date){
        String htmlMessage = "<h1>Informatics Team</h1><p>Find below the data entry report for "+date+".</p>";
        htmlMessage = htmlMessage + "<table border = 2><tr><td>Facility Name</td><td>Met Appointments</td><td>Missed Appointments</td><td>Unscheduled Visits</td></tr>";
        List<AppointmentReport> missedAppointments = new ArrayList<>();
        for (FacilityCount facilityCount:facilityCountList) {
            htmlMessage = htmlMessage +
                    "<tr>" +
                        "<td>"+facilityCount.getNameOfFacility() +"</td>"+
                        "<td>"+ facilityCount.getMetAppointments()+" ("+ facilityCount.getPercentageMetAppointments()+"%)</td>"+
                        "<td>"+ facilityCount.getMissedAppointments()+" ("+ facilityCount.getPercentageMissedAppointments()+"%)</td>"+
                        "<td>"+ facilityCount.getUnscheduledVisits()+"</td>"+
                    "</tr>";
            if(facilityCount.getMissedAppointmentDetails().size() != 0){
                missedAppointments.addAll(facilityCount.getMissedAppointmentDetails());
            }
        }
        if(missedAppointments.size() != 0){
            htmlMessage = htmlMessage + "</table>";
            htmlMessage = htmlMessage + "<br /><p>The following list below are the patients that Missed their appointment implying that they did not come to the " +
                    "facility</p>";
            htmlMessage = htmlMessage + "<table border = 2><tr><td></td><td>Identifiers</td><td>Given Name</td><td>Family Name</td><td>Location</td></tr>";
            int count= 1;
            for (AppointmentReport eachAppointmentReport: missedAppointments) {
                htmlMessage = htmlMessage +
                        "<tr>" +
                            "<td>"+count+"</td>"+
                            "<td>"+eachAppointmentReport.getIdentifiers() +"</td>"+
                            "<td>"+ eachAppointmentReport.getGivenName()+"</td>"+
                            "<td>"+ eachAppointmentReport.getFamilyName()+"</td>"+
                            "<td>"+ eachAppointmentReport.getLocation()+"</td>"+
                        "</tr>";
                count++;
            }
            htmlMessage = htmlMessage + "</table>";
        }
        return htmlMessage;
    }
}
