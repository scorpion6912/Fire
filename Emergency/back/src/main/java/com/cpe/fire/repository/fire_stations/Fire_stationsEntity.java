package com.cpe.fire.repository.fire_stations;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "fire_stations")
public class Fire_stationsEntity {
    @Id
    private String name;
    private double lon;
    private double lat;
    private int trucks_number;
    private String site_type;
}
