package com.cpe.fire.controller.incident;

import com.cpe.fire.domain.incident.Incident;
import org.springframework.stereotype.Component;

@Component
public class IncidentDtoMapper {

    protected IncidentDTO incidentToIncidentDTO(Incident incident) {
        return new IncidentDTO(incident.id(), incident.type(), incident.lon(), incident.lat(),
                incident.severity(), incident.date_start(), incident.date_end(), incident.status());
    }

    protected Incident incidentDtoToIncident(IncidentDTO incidentDTO) {
        // TODO: voir comment gerer la transformation en incident pour récup les dates
        //  (delivré par les données?)
        return new Incident(incidentDTO.getId(), incidentDTO.getType(), incidentDTO.getLon(),
                incidentDTO.getLat(), incidentDTO.getSeverity(), incidentDTO.getDate_start(),
                incidentDTO.getDate_end(), incidentDTO.getStatus());
    }
}
