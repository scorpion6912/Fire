package com.cpe.fire.repository.management_incidents;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManagementIncidentRepository {

    private final ManagementIncidentJpaRepository managementIncidentJpaRepository;

    public ManagementIncidentRepository(ManagementIncidentJpaRepository managementIncidentJpaRepository) {
        this.managementIncidentJpaRepository = managementIncidentJpaRepository;
    }

    public List<ManagementIncidentEntity> findAll(){
        return managementIncidentJpaRepository.findAll();
    }

    public void saveObject(ManagementIncidentEntity managementIncidentEntity){
        managementIncidentJpaRepository.save(managementIncidentEntity);
    }

    public void saveListObject(List<ManagementIncidentEntity> managementIncidentEntity){
        managementIncidentJpaRepository.saveAll(managementIncidentEntity);
    }
}
