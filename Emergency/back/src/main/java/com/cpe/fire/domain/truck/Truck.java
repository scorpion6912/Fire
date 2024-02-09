package com.cpe.fire.domain.truck;

public record Truck (Long id, String type, double lon, double lat, String fire_station,
                     String status, int nb_place) {
}
