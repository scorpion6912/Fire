package com.cpe.fire.controller.managementIncident;

import com.cpe.fire.domain.managementIncident.ManagementIncident;
import org.springframework.stereotype.Component;

@Component
public class ManagementIncidentDtoMapper {

    protected ManagementIncidentDTO managementIncidentToManagementIncidentDTO(ManagementIncident managementIncident) {
        return new ManagementIncidentDTO(managementIncident.id_incident(), managementIncident.nb_trucks(),
                managementIncident.travel_time(), managementIncident.on_site(), managementIncident.travel());
    }

    protected ManagementIncident managementIncidentDTOToManagementIncident(ManagementIncidentDTO managementIncidentDTO) {
        // TODO: voir comment gerer la transformation en incident pour récup les dates
        //  (delivré par les données?)
        return new ManagementIncident(managementIncidentDTO.getId_incident(), managementIncidentDTO.getNb_trucks(),
                managementIncidentDTO.getTravel_time(), managementIncidentDTO.isOn_site(), managementIncidentDTO.getTravel());
    }
}
