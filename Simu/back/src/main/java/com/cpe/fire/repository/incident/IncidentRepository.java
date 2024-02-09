package com.cpe.fire.repository.incident;

import com.cpe.fire.domain.incident.IIncidentRepository;
import com.cpe.fire.domain.incident.Incident;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class IncidentRepository implements IIncidentRepository {

    private final IncidentJpaRepository incidentJpaRepository;

    private final IncidentEntityMapper incidentEntityMapper;

    public IncidentRepository(IncidentJpaRepository incidentJpaRepository, IncidentEntityMapper incidentEntityMapper) {
        this.incidentJpaRepository = incidentJpaRepository;
        this.incidentEntityMapper = incidentEntityMapper;
    }

    @Override
    public List<Incident> findAll() {
        List<IncidentEntity> incidentEntityList = incidentJpaRepository.findAll();

        return incidentEntityList.stream()
                .map(incidentEntityMapper::incidentEntityToIncident)
                .toList();
    }

    @Override
    public List<IncidentEntity> findAllEntityOngoing() {
        return incidentJpaRepository.findAllEntityOngoing();
    }

    @Override
    public void insertIncident(List<IncidentEntity> incidentEntityList){
        incidentJpaRepository.saveAll(incidentEntityList);
    }

    @Override
    public void updateIncidentManagement(List<IncidentEntity> list) {

    }

    /* Just for reminder on @Query
    public void get (String name) {
        List<IncidentEntity> incidentEntityList = incidentJpaRepository.trucComplique(name);
    }
    */

}
