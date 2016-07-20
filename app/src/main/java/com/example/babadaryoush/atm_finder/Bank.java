package com.example.babadaryoush.atm_finder;

import android.content.Context;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Baba Daryoush on 27/06/2016.
 */

public class Bank {
    String name;
    double longitude;
    double latitude;
    String address;
    public static ArrayList<String> banks2String;


    public Bank (double latitude, double longitude, String name, String address){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setName(String name) {this.name = name;}
    public void setAddress(String address) {
        this.address = address;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {this.latitude = latitude;}

    public static ArrayList<Bank> getNearestBankList(ArrayList<Bank>bankList, LatLng myLoc){
        ArrayList<Bank> nearestBankList = new ArrayList<>();
        banks2String = new ArrayList<>();
        ArrayList<Bank> substitute = bankList;
        Bank nearestBank = null;
        Bank lastBank = null;

        if(substitute!=null) {
            for(int i=0; i<5; i++) {
                nearestBankLoop(substitute, nearestBankList, lastBank, myLoc, nearestBank);
            }
        }
        banks2String = bankListToString(nearestBankList, myLoc.latitude, myLoc.longitude);
        return nearestBankList;
    }

    public static void nearestBankLoop(ArrayList<Bank> substitute, ArrayList<Bank> nearestBankList ,Bank lastBank, LatLng myLoc, Bank nearestBank){
        for (Bank bank : substitute) {
            if (lastBank == null) {
                lastBank = bank;
                continue;
            }
            if (distance(bank.getLatitude(), bank.getLongitude(), myLoc.latitude, myLoc.longitude)
                    < distance(lastBank.getLatitude(), lastBank.getLongitude(), myLoc.latitude, myLoc.longitude)) {
                nearestBank = bank;
                lastBank = bank;
            } else {
                nearestBank = lastBank;
            }
        }
        if(nearestBank!=null) {
            nearestBankList.add(nearestBank);
            substitute.remove(nearestBank);
            nearestBank = null;
            lastBank = null;
        }
    }

    public static double distance(double fromLat, double fromLon, double toLat, double toLon) {
        double x1 = fromLat;
        double y1 =  fromLon;


        double x2 = toLat;
        double y2 =  toLon;
        int rayon = 6378; // km
        double dLat = Math.toRadians(x2-x1);
        double dLon = Math.toRadians(y2-y1);
        double lat1 = Math.toRadians(x1);
        double lat2 = Math.toRadians(x2);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        //System.out.println("SSSSSS: "+rayon*c);
        return rayon * c*1000;
    }


    public static ArrayList<String> bankListToString(ArrayList<Bank> lst, Double latitude, Double longitude){
        ArrayList<String> lstString = new ArrayList<>();

        for(Bank bank : lst){
            lstString.add(bank.getName()+" "+bank.getAddress()+" située à "
                    +String.format("%.1f", Bank.distance(latitude, longitude, bank.getLatitude(),
                    bank.getLongitude()))+"m\n"+"Données GPS: "+bank.getLatitude()+"/"+bank.getLongitude());
        }

        return lstString;
    }
}
