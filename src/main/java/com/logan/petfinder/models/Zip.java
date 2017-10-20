package com.logan.petfinder.models;

import javax.persistence.*;

@Entity
@Table( name = "zipcodes")
public class Zip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String zipcode;
    private String city;
    private String state;
    private double lat;
    private double lng;
    private double timezone;
    private double dst;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public double getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public double getDst() {
        return dst;
    }

    public void setDst(int dst) {
        this.dst = dst;
    }
}
