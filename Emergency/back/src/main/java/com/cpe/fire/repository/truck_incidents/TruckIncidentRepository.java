package com.cpe.fire.repository.truck_incidents;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TruckIncidentRepository {
    private final TruckIncidentJPARepository truckIncidentJPARepository;

    public TruckIncidentRepository(TruckIncidentJPARepository truckIncidentJPARepository) {
        this.truckIncidentJPARepository = truckIncidentJPARepository;
    }

    public List<TruckIncidentEntity> findAll(){
        return truckIncidentJPARepository.findAll();
    }

    public void saveObject(TruckIncidentEntity truckIncidentEntity){
        truckIncidentJPARepository.save(truckIncidentEntity);
    }

    public void saveListObject(List<TruckIncidentEntity> truckIncidentEntities){
        truckIncidentJPARepository.saveAll(truckIncidentEntities);
    }
}
