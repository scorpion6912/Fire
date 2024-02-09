package com.cpe.fire.repository.sensor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "sensors")
public class SensorEntity {
    @Id
    private Long id;
    private double lon;
    private double lat;
    private double value;
    private Timestamp time;

    public SensorEntity() {
    }

    @Override
    public String toString() {
        return "SensorEntity{" +
                "id=" + id +
                ", lon=" + lon +
                ", lat=" + lat +
                ", value=" + value +
                ", time=" + time +
                '}';
    }
}
