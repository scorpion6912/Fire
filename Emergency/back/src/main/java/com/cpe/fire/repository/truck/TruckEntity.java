package com.cpe.fire.repository.truck;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "trucks")
public class TruckEntity {
    @GeneratedValue
    @Id
    private Long id;

    private String type;

    private double lon;

    private double lat;

    private String fire_station;

    private String status;

    private int nb_place;
}
