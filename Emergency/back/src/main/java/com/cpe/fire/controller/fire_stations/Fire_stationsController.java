package com.cpe.fire.controller.fire_stations;

import com.cpe.fire.domain.fire_stations.Fire_stationsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stations")
@CrossOrigin(origins = "*")
public class Fire_stationsController {

    private final Fire_stationsDtoMapper fireStationsDtoMapper;

    private final Fire_stationsService fireStationsService;

    public Fire_stationsController(Fire_stationsDtoMapper fireStationsDtoMapper,
                                   Fire_stationsService fireStationsService) {
        this.fireStationsDtoMapper = fireStationsDtoMapper;
        this.fireStationsService = fireStationsService;
    }

    @GetMapping
    public List<Fire_stationsDTO> getAllFireStations() {
        return fireStationsService.getAllFireStations().stream().map(
                fireStationsDtoMapper::fireStationsToFireStationDTO
        ).toList();
    }
}
