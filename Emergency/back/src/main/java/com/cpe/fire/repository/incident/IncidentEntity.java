package com.cpe.fire.repository.incident;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity(name = "incidents")
public class IncidentEntity {
    @GeneratedValue
    @Id
    private Long id;

    private String type;

    private double lon;

    private double lat;

    private double severity;

    private Date date_start;

    private Date date_end;

    private String status;

    public IncidentEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public double getSeverity() {
        return severity;
    }

    public Date getDate_start() {
        return date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setSeverity(double severity) {
        this.severity = severity;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IncidentEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", severity=" + severity +
                ", date_start=" + date_start +
                ", date_end=" + date_end +
                ", status='" + status + '\'' +
                '}';
    }
}
