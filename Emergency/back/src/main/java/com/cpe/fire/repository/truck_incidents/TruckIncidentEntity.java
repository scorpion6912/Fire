package com.cpe.fire.repository.truck_incidents;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="truck_incidents")
public class TruckIncidentEntity {
    @Id
    private Long id_truck;
    private Long id_incident;

    public TruckIncidentEntity() {
    }

    @Override
    public String toString() {
        return "truck_incident{" +
                "id incident=" + id_incident +
                ", id truck=" + id_truck +
                '}';
    }
}
