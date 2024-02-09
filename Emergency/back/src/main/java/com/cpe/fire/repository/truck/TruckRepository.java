package com.cpe.fire.repository.truck;

import com.cpe.fire.domain.truck.ITruckRepository;
import com.cpe.fire.domain.truck.Truck;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TruckRepository implements ITruckRepository {

    public final ITruckJpaRepository iTruckJpaRepository;

    public final TruckEntityMapper truckEntityMapper;

    public TruckRepository(ITruckJpaRepository iTruckJpaRepository, TruckEntityMapper truckEntityMapper) {
        this.iTruckJpaRepository = iTruckJpaRepository;
        this.truckEntityMapper = truckEntityMapper;
    }

    public List<Truck> findAll() {
        List<TruckEntity> truckEntityList = iTruckJpaRepository.findAll();

        return truckEntityList.stream()
                .map(truckEntityMapper::truckEntityToTruck)
                .toList();
    }
}
