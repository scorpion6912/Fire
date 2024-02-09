package com.cpe.fire.domain.incident;

import com.cpe.fire.repository.incident.IncidentEntity;

import java.util.List;

public interface IIncidentRepository {
    List<Incident> findAll();
    List<IncidentEntity> findAllEntityOngoing();
    void insertIncident(List<IncidentEntity> list);

    void updateIncidentManagement(List<IncidentEntity> list);
}
