package com.cpe.fire.controller.fire_stations;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fire_stationsDTO {
    private String name;
    private double lon;
    private double lat;
    private int trucks_number;
    private String site_type;

    public Fire_stationsDTO(String name, double lon, double lat, int trucks_number, String site_type) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.trucks_number = trucks_number;
        this.site_type = site_type;
    }
}
