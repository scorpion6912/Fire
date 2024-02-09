package com.cpe.fire.domain.fire_stations;

import com.cpe.fire.repository.fire_stations.Fire_stationsEntity;

import java.util.List;

public interface IFire_stationsRepository {
    List<Fire_stations> findAll();

    List<Fire_stationsEntity> findAllEntity();

    void saveOne(Fire_stationsEntity fireStationsEntity);
}
