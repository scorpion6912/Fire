package com.cpe.fire.repository.truck_incidents;

import com.cpe.fire.domain.fire_stations.Fire_stations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TruckIncidentJPARepository extends JpaRepository<TruckIncidentEntity, Long>{

}
