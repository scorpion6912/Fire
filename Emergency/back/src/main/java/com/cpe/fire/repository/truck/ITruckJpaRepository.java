package com.cpe.fire.repository.truck;

import com.cpe.fire.domain.truck.Truck;
import com.cpe.fire.repository.truck_incidents.TruckIncidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITruckJpaRepository extends JpaRepository<TruckEntity, Long> {
    @Query("Select i from trucks i where i.fire_station = ?1")
    List<TruckEntity> findtruckincident(String f);
}
