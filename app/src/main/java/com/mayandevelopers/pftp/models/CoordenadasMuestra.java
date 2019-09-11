package com.mayandevelopers.pftp.models;

public class CoordenadasMuestra {


    String id;
    double lat;
    double lng;


    public CoordenadasMuestra(String id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }


}
