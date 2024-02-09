package com.cpe.fire.domain.sensor;


import com.cpe.fire.repository.sensor.SensorEntity;

import java.util.List;

public interface ISensorRepository {
    List<Sensor> findAll();
    void insertSensor(List<SensorEntity> list);
}
