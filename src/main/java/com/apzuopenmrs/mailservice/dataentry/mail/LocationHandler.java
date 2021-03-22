package com.apzuopenmrs.mailservice.dataentry.mail;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class LocationHandler {
    public HashMap<Integer,String> getUpperNenoLocations(){
        HashMap<Integer,String> upperNeno = new HashMap<>();
        upperNeno.put(2,"Neno District Hospital");
        upperNeno.put(3, "Magaleta HC");
        upperNeno.put(4,"Neno Mission HC");
        upperNeno.put(5,"Matandani Rural Health Center");
        upperNeno.put(34,"Ligowe HC");
        upperNeno.put(17,"Luwani RHC");
        upperNeno.put(20,"Nsambe HC");
        upperNeno.put(57,"Dambe Clinic");

        return upperNeno;
    }
    public HashMap<Integer,String> getLowerNenoLocations(){
        HashMap<Integer,String> lowerNeno = new HashMap<>();
        lowerNeno.put(16,"Lisungwi Community Hospital");
        lowerNeno.put(18, "Chifunga HC");
        lowerNeno.put(35,"Zalewa HC");
        lowerNeno.put(37,"Midzemba HC");
        lowerNeno.put(22,"Matope HC");
        lowerNeno.put(21,"Nkhula Falls RHC");
        return lowerNeno;
    }
}
