package com.cpe.fire.repository.management_incidents;

import com.cpe.fire.domain.managementIncident.ManagementIncident;
import org.springframework.stereotype.Component;

@Component
public class ManagementIncidentEntityMapper {

    public ManagementIncident managementIncidentEntityToManagementIncident(ManagementIncidentEntity managementIncidentEntity) {
        return new ManagementIncident(managementIncidentEntity.getId_incident(), managementIncidentEntity.getNb_trucks(),
                managementIncidentEntity.getTravel_time(), managementIncidentEntity.isOn_site(), managementIncidentEntity.getTravel());
    }

    public ManagementIncidentEntity managementIncidentToManagementIncidentEntity(ManagementIncident managementIncident){
        ManagementIncidentEntity managementIncidentEntity = new ManagementIncidentEntity();
        managementIncidentEntity.setId_incident(managementIncident.id_incident());
        managementIncidentEntity.setNb_trucks(managementIncident.nb_trucks());
        managementIncidentEntity.setTravel_time(managementIncident.travel_time());
        managementIncidentEntity.setOn_site(managementIncident.on_site());
        managementIncidentEntity.setTravel(managementIncident.travel());
        return managementIncidentEntity;
    }

}
