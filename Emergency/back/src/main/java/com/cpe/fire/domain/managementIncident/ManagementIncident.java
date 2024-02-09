package com.cpe.fire.domain.managementIncident;

public record ManagementIncident(Long id_incident, int nb_trucks, double travel_time, boolean on_site, String travel) {
}
