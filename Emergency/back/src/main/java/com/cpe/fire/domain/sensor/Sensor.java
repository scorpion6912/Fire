package com.cpe.fire.domain.sensor;

import java.sql.Timestamp;

public record Sensor(Long id, double lon, double lat, double value, Timestamp time) {
}
