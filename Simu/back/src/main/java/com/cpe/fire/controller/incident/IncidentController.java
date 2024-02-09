package com.cpe.fire.controller.incident;

import com.cpe.fire.domain.incident.IncidentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/incident")
@CrossOrigin(origins = "*")
public class IncidentController {

    private final IncidentService incidentService;

    private final IncidentDtoMapper incidentDtoMapper;

    public IncidentController(IncidentService incidentService, IncidentDtoMapper incidentDtoMapper) {
        this.incidentService = incidentService;
        this.incidentDtoMapper = incidentDtoMapper;
    }

    @GetMapping
    public List<IncidentDTO> getAllIncident() {
        return incidentService.getAllIncident().stream()
                .map(incidentDtoMapper::incidentToIncidentDTO)
                .toList();
    }

    @GetMapping("/new")
    public void insertIncident(String data){
        System.out.println(data);
        incidentService.insertIncident(data);
    }

    @PutMapping("/update")
    public void updateIncidentManagement(String data){
        incidentService.updateIncidentManagement(data);
    }
}
