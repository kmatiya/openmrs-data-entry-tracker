package com.apzuopenmrs.mailservice.dataentry.mail;

import com.apzuopenmrs.mailservice.dataentry.model.AppointmentReport;

import java.util.List;

public class FacilityCount {
    private String nameOfFacility;
    private int metAppointments;
    private int missedAppointments;
    private int unscheduledVisits;
    private double percentageMetAppointments;
    private double percentageMissedAppointments;
    private double percentageUnscheduledVisits;

    private List<AppointmentReport> missedAppointmentDetails;


    public String getNameOfFacility() {
        return nameOfFacility;
    }

    public void setNameOfFacility(String nameOfFacility) {
        this.nameOfFacility = nameOfFacility;
    }

    public int getMetAppointments() {
        return metAppointments;
    }

    public void setMetAppointments(int metAppointments) {
        this.metAppointments = metAppointments;
    }

    public int getMissedAppointments() {
        return missedAppointments;
    }

    public void setMissedAppointments(int missedAppointments) {
        this.missedAppointments = missedAppointments;
    }

    public int getUnscheduledVisits() {
        return unscheduledVisits;
    }

    public void setUnscheduledVisits(int unscheduledVisits) {
        this.unscheduledVisits = unscheduledVisits;
    }

    public double getPercentageMetAppointments() {
        return percentageMetAppointments;
    }

    public void setPercentageMetAppointments(double percentageMetAppointments) {
        this.percentageMetAppointments = percentageMetAppointments;
    }

    public double getPercentageMissedAppointments() {
        return percentageMissedAppointments;
    }

    public void setPercentageMissedAppointments(double percentageMissedAppointments) {
        this.percentageMissedAppointments = percentageMissedAppointments;
    }

    public double getPercentageUnscheduledVisits() {
        return percentageUnscheduledVisits;
    }

    public void setPercentageUnscheduledVisits(double percentageUnscheduledVisits) {
        this.percentageUnscheduledVisits = percentageUnscheduledVisits;
    }
    public List<AppointmentReport> getMissedAppointmentDetails() {
        return missedAppointmentDetails;
    }

    public void setMissedAppointmentDetails(List<AppointmentReport> missedAppointmentDetails) {
        this.missedAppointmentDetails = missedAppointmentDetails;
    }
}
