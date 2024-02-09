package com.cpe.fire.controller.managementIncident;

import com.cpe.fire.domain.managementIncident.ManagementIncidentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/managementIncident")
@CrossOrigin(origins = "*")
public class ManagementIncidentController {

    private final ManagementIncidentService managementIncidentService;

    private final ManagementIncidentDtoMapper managementIncidentDtoMapper;

    public ManagementIncidentController(ManagementIncidentService managementIncidentService, ManagementIncidentDtoMapper managementIncidentDtoMapper) {
        this.managementIncidentService = managementIncidentService;
        this.managementIncidentDtoMapper = managementIncidentDtoMapper;
    }

    @GetMapping
    public List<ManagementIncidentDTO> getAllManagementIncident() {
        return managementIncidentService.getAllManagementIncident().stream()
                .map(managementIncidentDtoMapper::managementIncidentToManagementIncidentDTO)
                .toList();
    }
}
