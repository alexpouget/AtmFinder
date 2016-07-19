package com.example.babadaryoush.atm_finder;

import android.content.Context;

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

        if(substitute!=null) {
            Bank nearestBank = null;
            Bank lastBank = null;
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
            nearestBankList.add(nearestBank);
            System.out.println("COUNTOR: " + substitute.size() + ", TF: " + substitute.contains(nearestBank));
            substitute.remove(nearestBank);
            System.out.println("COUNTORZZ: " + substitute.size() + ", TF: " + substitute.contains(nearestBank));
            banks2String.add(nearestBank.getName() + " " + nearestBank.getAddress() + " située à "
                    + String.format("%.1f", nearestBank.distance(myLoc.latitude, myLoc.longitude,
                    nearestBank.getLatitude(), nearestBank.getLongitude()) * 0.01) + "m");
            nearestBank = null;
            lastBank = null;

            if(substitute.size()>0) {
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
                nearestBankList.add(nearestBank);
                substitute.remove(nearestBank);
                banks2String.add(nearestBank.getName() + " " + nearestBank.getAddress() + " située à "
                        + String.format("%.1f", nearestBank.distance(myLoc.latitude, myLoc.longitude,
                        nearestBank.getLatitude(), nearestBank.getLongitude()) * 0.01) + "m");
                nearestBank = null;
                lastBank = null;

                if(substitute.size()>0) {
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
                    nearestBankList.add(nearestBank);
                    substitute.remove(nearestBank);
                    banks2String.add(nearestBank.getName() + " " + nearestBank.getAddress() + " située à "
                            + String.format("%.1f", nearestBank.distance(myLoc.latitude, myLoc.longitude,
                            nearestBank.getLatitude(), nearestBank.getLongitude()) * 0.01) + "m");
                    nearestBank = null;
                    lastBank = null;

                    if(substitute.size()>0) {
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
                        nearestBankList.add(nearestBank);
                        substitute.remove(nearestBank);
                        banks2String.add(nearestBank.getName() + " " + nearestBank.getAddress() + " située à "
                                + String.format("%.1f", nearestBank.distance(myLoc.latitude, myLoc.longitude,
                                nearestBank.getLatitude(), nearestBank.getLongitude()) * 0.01) + "m");
                        nearestBank = null;
                        lastBank = null;

                        if(substitute.size()>0) {
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
                            nearestBankList.add(nearestBank);
                            banks2String.add(nearestBank.getName() + " " + nearestBank.getAddress() + " située à "
                                    + String.format("%.1f", nearestBank.distance(myLoc.latitude, myLoc.longitude,
                                    nearestBank.getLatitude(), nearestBank.getLongitude()) * 0.01) + "m");
                        }
                    }
                }
            }
        }
        return nearestBankList;
    }

   public static double distance(double fromLat, double fromLon, double toLat, double toLon) {
        double radius = 6378100 ;   // approximate Earth radius, *in meters*
        double deltaLat = toLat - fromLat;
        double deltaLon = toLon - fromLon;
        double angle = 2 * Math.asin( Math.sqrt(
                Math.pow(Math.sin(deltaLat/2), 2) +
                        Math.cos(fromLat) * Math.cos(toLat) *
                                Math.pow(Math.sin(deltaLon/2), 2) ) );
        return radius * angle;
    }

}
