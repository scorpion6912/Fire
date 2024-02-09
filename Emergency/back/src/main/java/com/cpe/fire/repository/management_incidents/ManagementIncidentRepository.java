package com.cpe.fire.repository.management_incidents;

import com.cpe.fire.domain.managementIncident.IManagementIncidentRepository;
import com.cpe.fire.domain.managementIncident.ManagementIncident;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManagementIncidentRepository implements IManagementIncidentRepository {

    private final ManagementIncidentJpaRepository managementIncidentJpaRepository;
    private final ManagementIncidentEntityMapper managementIncidentEntityMapper;

    public ManagementIncidentRepository(ManagementIncidentJpaRepository managementIncidentJpaRepository, ManagementIncidentEntityMapper managementIncidentEntityMapper) {
        this.managementIncidentJpaRepository = managementIncidentJpaRepository;
        this.managementIncidentEntityMapper = managementIncidentEntityMapper;
    }

    public List<ManagementIncident> findAll(){
        List<ManagementIncidentEntity> managementIncidentEntityList = managementIncidentJpaRepository.findAll();

        return managementIncidentEntityList.stream()
                .map(managementIncidentEntityMapper::managementIncidentEntityToManagementIncident)
                .toList();
    }

    public void saveObject(ManagementIncidentEntity managementIncidentEntity){
        managementIncidentJpaRepository.save(managementIncidentEntity);
    }

    public void saveListObject(List<ManagementIncidentEntity> managementIncidentEntity){
        managementIncidentJpaRepository.saveAll(managementIncidentEntity);
    }
}
