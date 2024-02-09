package com.cpe.fire.repository.incident;

import com.cpe.fire.domain.incident.Incident;
import org.springframework.stereotype.Component;

@Component
public class IncidentEntityMapper {

    public Incident incidentEntityToIncident(IncidentEntity incidentEntity) {
        return new Incident(incidentEntity.getId(), incidentEntity.getType(), incidentEntity.getLon(),
                incidentEntity.getLat(), incidentEntity.getSeverity(),
                incidentEntity.getDate_start(), incidentEntity.getDate_end(), incidentEntity.getStatus());
    }

    public IncidentEntity incidentToIncidentEntity(Incident incident){
        IncidentEntity incidentEntity = new IncidentEntity();
        incidentEntity.setType(incident.type());
        incidentEntity.setLon(incident.lon());
        incidentEntity.setLat(incident.lat());
        incidentEntity.setSeverity(incident.severity());
        incidentEntity.setDate_start(incident.date_start());
        incidentEntity.setDate_end(incident.date_end());
        incidentEntity.setStatus(incident.status());
        return incidentEntity;
    }

}
