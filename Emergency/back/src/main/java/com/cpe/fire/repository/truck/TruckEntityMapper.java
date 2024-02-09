package com.cpe.fire.repository.truck;

import com.cpe.fire.domain.truck.Truck;
import org.springframework.stereotype.Component;

@Component
public class TruckEntityMapper {

    public TruckEntity truckToTruckEntity(Truck truck) {
        TruckEntity truckEntity = new TruckEntity();
        truckEntity.setLat(truck.lat());
        truckEntity.setLon(truck.lon());
        truckEntity.setType(truck.type());
        truckEntity.setStatus(truck.status());
        truckEntity.setFire_station(truck.fire_station());
        truckEntity.setNb_place(truck.nb_place());
        return truckEntity;
    }

    public Truck truckEntityToTruck(TruckEntity truckEntity) {
        return new Truck(truckEntity.getId(), truckEntity.getType(),
                truckEntity.getLon(), truckEntity.getLat(),
                truckEntity.getFire_station(), truckEntity.getStatus(),
                truckEntity.getNb_place());
    }
}
