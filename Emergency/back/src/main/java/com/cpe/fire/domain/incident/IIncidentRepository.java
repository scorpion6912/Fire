package com.cpe.fire.domain.incident;

import com.cpe.fire.repository.incident.IncidentEntity;

import java.util.List;

public interface IIncidentRepository {
    List<Incident> findAll();
    List<IncidentEntity> findAllEntityTODO();
    List<Incident> findAllEntityONGOING();

    void saveOne(IncidentEntity incidentEntity);
    void insertIncident(List<IncidentEntity> list);

    void updateIncidentManagement(List<IncidentEntity> list);
}
