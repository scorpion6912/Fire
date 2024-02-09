package com.cpe.fire.repository.fire_stations;

import com.cpe.fire.domain.fire_stations.Fire_stations;
import org.springframework.stereotype.Component;

@Component
public class Fire_stationsEntityMapper {

    public Fire_stations fireStationsEntityToFireStations (Fire_stationsEntity fireStationsEntity) {
        return new Fire_stations(fireStationsEntity.getName(), fireStationsEntity.getLon(),
                fireStationsEntity.getLat(), fireStationsEntity.getTrucks_number(),
                fireStationsEntity.getSite_type());
    }
}
