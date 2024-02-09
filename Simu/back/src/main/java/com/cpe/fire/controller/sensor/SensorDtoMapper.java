package com.cpe.fire.controller.sensor;

import com.cpe.fire.domain.sensor.Sensor;
import org.springframework.stereotype.Component;

@Component
public class SensorDtoMapper {

    public SensorDTO sensorToSensorDto(Sensor sensor) {
        return new SensorDTO(sensor.id(), sensor.lon(), sensor.lat(), sensor.value(), sensor.time());
    }
}
