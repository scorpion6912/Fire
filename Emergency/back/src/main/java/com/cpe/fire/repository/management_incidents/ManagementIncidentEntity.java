package com.cpe.fire.repository.management_incidents;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="management_incidents")
public class ManagementIncidentEntity {
    @Id
    private Long id_incident;
    private int nb_trucks;
    private double travel_time;
    private boolean on_site;
    private String travel;

    public ManagementIncidentEntity() {
    }

    @Override
    public String toString() {
        return "ManagementIncidentEntity{" +
                "id=" + id_incident +
                ", nb_trucks=" + nb_trucks +
                ", travel_time=" + travel_time +
                ", on_site=" + on_site +
                ", travel='" + travel + '\'' +
                '}';
    }
}
