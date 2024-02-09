package com.cpe.fire.repository.sensor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity(name = "sensors")
public class SensorEntity {
    @GeneratedValue
    @Id
    private Long id;
    private double lon;
    private double lat;
    private double value;
    private Timestamp time;

    public SensorEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", lon=" + lon +
                ", lat=" + lat +
                ", value=" + value +
                ", time=" + time +
                '}';
    }
}
