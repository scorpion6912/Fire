package com.cpe.fire.domain.fire_stations;

import com.cpe.fire.controller.fire_stations.Fire_stationsDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Fire_stationsService {
    private final Fire_stationsDtoMapper fireStationsDtoMapper;
    private final IFire_stationsRepository iFireStationsRepository;

    public Fire_stationsService(Fire_stationsDtoMapper fireStationsDtoMapper,
                                IFire_stationsRepository iFireStationsRepository) {
        this.fireStationsDtoMapper = fireStationsDtoMapper;
        this.iFireStationsRepository = iFireStationsRepository;
    }

    public List<Fire_stations> getAllFireStations() {
        return iFireStationsRepository.findAll();
    }
}
