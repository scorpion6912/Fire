package com.cpe.fire.repository.sensor;

import com.cpe.fire.domain.sensor.Sensor;
import org.springframework.stereotype.Component;

@Component
public class SensorEntityMapper {

    public SensorEntity sensorToSensorEntity(Sensor sensor) {
        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.setId(sensor.id());
        sensorEntity.setLon(sensor.lon());
        sensorEntity.setLat(sensor.lat());
        sensorEntity.setValue(sensor.value());
        sensorEntity.setTime(sensor.time());
        return sensorEntity;
    }

    public Sensor sensorEntityToSensor(SensorEntity sensorEntity) {
        return new Sensor(sensorEntity.getId(), sensorEntity.getLon(), sensorEntity.getLat(),
                sensorEntity.getValue(), sensorEntity.getTime());
    }
}
