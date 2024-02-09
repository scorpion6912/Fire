package com.cpe.fire.controller.incident;

import java.util.Date;

public class IncidentDTO {

    private Long id;

    private String type;

    private double lon;

    private double lat;

    private double severity;

    public IncidentDTO(Long id, String type, double lon, double lat, double severity) {
        this.id = id;
        this.type = type;
        this.lon = lon;
        this.lat = lat;
        this.severity = severity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public double getSeverity() {
        return severity;
    }

    public void setSeverity(double severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "IncidentDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", severity=" + severity +
                '}';
    }
}
