package com.cpe.fire.controller.sensor;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SensorDTO {
    private Long id;
    private double lon;
    private double lat;
    private double value;
    private Timestamp time;

    public SensorDTO(Long id, double lon, double lat, double value, Timestamp time) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.value = value;
        this.time = time;
    }
}
