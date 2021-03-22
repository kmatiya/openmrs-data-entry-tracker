package com.apzuopenmrs.mailservice.dataentry.mail;

import com.apzuopenmrs.mailservice.dataentry.model.AppointmentReport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FacilityAggregator {
    public FacilityCount getAggregatedData(String location, List<AppointmentReport> appointmentReportList){
        int metAppointments = 0;
        int missedAppointments = 0;
        int unscheduledVisits = 0;
        List<AppointmentReport> appointmentReports = new ArrayList<>();

        for (AppointmentReport eachVisit: appointmentReportList) {
            if(eachVisit.getCameToFacility().equals("YES") && eachVisit.getWasThisAppointmentDay().equals("YES")){
                metAppointments++;
            }
            if(eachVisit.getCameToFacility().equals("NO") && eachVisit.getWasThisAppointmentDay().equals("YES")){
                missedAppointments++;
                AppointmentReport appointmentReport = new AppointmentReport();
                appointmentReport.setLocation(location);
                appointmentReport.setGivenName(eachVisit.getGivenName());
                appointmentReport.setFamilyName(eachVisit.getFamilyName());
                appointmentReport.setIdentifiers(eachVisit.getIdentifiers());
                appointmentReports.add(appointmentReport);
            }
            if(eachVisit.getCameToFacility().equals("YES") && eachVisit.getWasThisAppointmentDay().equals("NO")){
                unscheduledVisits++;
            }
        }
        double total = metAppointments + missedAppointments;
        FacilityCount facilityCount = new FacilityCount();
        facilityCount.setNameOfFacility(location);
        facilityCount.setMetAppointments(metAppointments);
        facilityCount.setMissedAppointments(missedAppointments);
        facilityCount.setUnscheduledVisits(unscheduledVisits);
        facilityCount.setPercentageMetAppointments(Math.round(metAppointments * 100 / total));
        facilityCount.setPercentageMissedAppointments(Math.round(missedAppointments * 100 / total));
        facilityCount.setPercentageUnscheduledVisits(unscheduledVisits);
        facilityCount.setMissedAppointmentDetails(appointmentReports);
        return facilityCount;
    }
}
