package com.cpe.fire.domain.managementIncident;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ManagementIncidentService {

    private final IManagementIncidentRepository iManagementIncidentRepository;


    public ManagementIncidentService(IManagementIncidentRepository iManagementIncidentRepository)
    {
        this.iManagementIncidentRepository = iManagementIncidentRepository;

    }

    public List<ManagementIncident> getAllManagementIncident() {
        return iManagementIncidentRepository.findAll();
    }
}
