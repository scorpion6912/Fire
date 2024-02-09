package com.cpe.fire.controller.managementIncident;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagementIncidentDTO {

    private Long id_incident;

    private int nb_trucks;

    private double travel_time;

    private boolean on_site;

    private String travel;

    public ManagementIncidentDTO(Long id_incident, int nb_trucks, double travel_time, boolean on_site, String travel) {
        this.id_incident = id_incident;
        this.nb_trucks = nb_trucks;
        this.travel_time = travel_time;
        this.on_site = on_site;
        this.travel = travel;
    }

    @Override
    public String toString() {
        return "ManagementIncidentDTO{" +
                "id_incident=" + id_incident +
                ", nb_trucks='" + nb_trucks + '\'' +
                ", travel_time=" + travel_time +
                ", on_site=" + on_site +
                ", travel=" + travel +
                '}';
    }
}
