package com.cpe.fire.repository.sensor;

import com.cpe.fire.domain.sensor.ISensorRepository;
import com.cpe.fire.domain.sensor.Sensor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SensorRepository implements ISensorRepository {

    private final ISensorJpaRepository iSensorJpaRepository;

    private final SensorEntityMapper sensorEntityMapper;

    public SensorRepository(ISensorJpaRepository iSensorJpaRepository, SensorEntityMapper sensorEntityMapper) {
        this.iSensorJpaRepository = iSensorJpaRepository;
        this.sensorEntityMapper = sensorEntityMapper;
    }

    @Override
    public List<Sensor> findAll(){
        List<SensorEntity> incidentsensorEntityList = iSensorJpaRepository.findAll();

        return incidentsensorEntityList.stream()
                .map(sensorEntityMapper::sensorEntityToSensor)
                .toList();
    }

    @Override
    public void insertSensor(List<SensorEntity> sensorRepositoryList) {
        iSensorJpaRepository.saveAll(sensorRepositoryList);
    }

    @Override
    public void sensorCreation(){

    }

}
