package com.cpe.fire.domain.incident;

import java.sql.Date;

public record Incident(Long id, String type, double lon, double lat,
                       double severity, Date date_start, Date date_end, String status) {
}
