package com.apzuopenmrs.mailservice.dataentry.model;

public class AppointmentReport {
    private String identifiers;
    private String givenName;
    private String familyName;
    private String cameToFacility;
    private String wasThisAppointmentDay;
    private String location;

    public String getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(String identifiers) {
        this.identifiers = identifiers;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getCameToFacility() {
        return cameToFacility;
    }

    public void setCameToFacility(String cameToFacility) {
        this.cameToFacility = cameToFacility;
    }

    public String getWasThisAppointmentDay() {
        return wasThisAppointmentDay;
    }

    public void setWasThisAppointmentDay(String wasThisAppointmentDay) {
        this.wasThisAppointmentDay = wasThisAppointmentDay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
