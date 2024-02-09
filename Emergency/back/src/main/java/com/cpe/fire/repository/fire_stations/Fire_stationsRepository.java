package com.cpe.fire.repository.fire_stations;

import com.cpe.fire.domain.fire_stations.Fire_stations;
import com.cpe.fire.domain.fire_stations.IFire_stationsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Fire_stationsRepository implements IFire_stationsRepository {

    private final Fire_stationsJpaRepository fire_stationsJpaRepository;

    private final Fire_stationsEntityMapper fire_stationsEntityMapper;

    public Fire_stationsRepository(Fire_stationsJpaRepository fire_stationsJpaRepository,
                                   Fire_stationsEntityMapper fire_stationsEntityMapper) {
        this.fire_stationsJpaRepository = fire_stationsJpaRepository;
        this.fire_stationsEntityMapper = fire_stationsEntityMapper;
    }

    @Override
    public List<Fire_stations> findAll() {
        List<Fire_stationsEntity> fireStationsEntities = fire_stationsJpaRepository.findAll();

        return fireStationsEntities.stream()
                .map(fire_stationsEntityMapper::fireStationsEntityToFireStations)
                .toList();
    }

    @Override
    public List<Fire_stationsEntity> findAllEntity() {
        return fire_stationsJpaRepository.findAll();
    }

    @Override
    public void saveOne(Fire_stationsEntity fireStationsEntity){
        fire_stationsJpaRepository.save(fireStationsEntity);
    }
}
