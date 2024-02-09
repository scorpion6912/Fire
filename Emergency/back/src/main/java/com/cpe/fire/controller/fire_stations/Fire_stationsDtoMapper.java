package com.cpe.fire.controller.fire_stations;

import com.cpe.fire.domain.fire_stations.Fire_stations;
import org.springframework.stereotype.Component;

@Component
public class Fire_stationsDtoMapper {

    public Fire_stationsDTO fireStationsToFireStationDTO(Fire_stations fireStations){
        return new Fire_stationsDTO(fireStations.name(), fireStations.lon(), fireStations.lat(),
                fireStations.trucks_number(), fireStations.site_type());
    }
}
