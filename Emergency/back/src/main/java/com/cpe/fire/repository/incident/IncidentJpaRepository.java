package com.cpe.fire.repository.incident;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncidentJpaRepository extends JpaRepository<IncidentEntity, Long> {

    @Query("Select i from incidents i where i.status = 'TODO'")
    List<IncidentEntity> findAllEntityTODO();

    @Query("Select i from incidents i where i.status = 'ONGOING'")
    List<IncidentEntity> findAllEntityONGOING();

}
