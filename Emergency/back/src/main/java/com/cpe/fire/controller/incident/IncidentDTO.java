package com.cpe.fire.controller.incident;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class IncidentDTO {

    private Long id;

    private String type;

    private double lon;

    private double lat;

    private double severity;

    private Date date_start;
    private Date date_end;
    private String status;

    public IncidentDTO(Long id, String type, double lon, double lat, double severity, Date date_start, Date date_end, String status) {
        this.id = id;
        this.type = type;
        this.lon = lon;
        this.lat = lat;
        this.severity = severity;
        this.date_start = date_start;
        this.date_end = date_end;
        this.status = status;
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
