package com.cpe.fire.domain.truck;

import com.cpe.fire.repository.fire_stations.Fire_stationsEntity;
import com.cpe.fire.repository.fire_stations.Fire_stationsRepository;
import com.cpe.fire.repository.incident.IncidentEntity;
import com.cpe.fire.repository.incident.IncidentRepository;
import com.cpe.fire.repository.management_incidents.ManagementIncidentEntity;
import com.cpe.fire.repository.management_incidents.ManagementIncidentRepository;
import com.cpe.fire.repository.truck.ITruckJpaRepository;
import com.cpe.fire.repository.truck.TruckEntity;
import com.cpe.fire.repository.truck.TruckEntityMapper;
import com.cpe.fire.repository.truck_incidents.TruckIncidentEntity;
import com.cpe.fire.repository.truck_incidents.TruckIncidentJPARepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.mapbox.geojson.Point;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.LegStep;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import retrofit2.Response;

@Service
@Transactional
public class TruckService {

    private final ITruckRepository iTruckRepository;

    private final TruckEntityMapper truckEntityMapper;

    private final IncidentRepository incidentRepository;

    private final Fire_stationsRepository fireStationsRepository;

    private final ManagementIncidentRepository managementIncidentRepository;
    private final TruckIncidentJPARepository truckIncidentJPARepository;
    private final ITruckJpaRepository iTruckJpaRepository;

    public TruckService(ITruckRepository iTruckRepository, TruckEntityMapper truckEntityMapper,
                        IncidentRepository incidentRepository, Fire_stationsRepository fireStationsRepository, ManagementIncidentRepository managementIncidentRepository, TruckIncidentJPARepository truckIncidentJPARepository, ITruckJpaRepository iTruckJpaRepository) {
        this.iTruckRepository = iTruckRepository;
        this.truckEntityMapper = truckEntityMapper;
        this.incidentRepository = incidentRepository;
        this.fireStationsRepository = fireStationsRepository;
        this.managementIncidentRepository = managementIncidentRepository;
        this.truckIncidentJPARepository = truckIncidentJPARepository;
        this.iTruckJpaRepository = iTruckJpaRepository;
    }

    public void manageNewIncident() {
        List<IncidentEntity> incidentEntityList = incidentRepository.findAllEntityTODO();
        List<Fire_stationsEntity> fireStationsEntityList = fireStationsRepository.findAllEntity();
        for (IncidentEntity incident : incidentEntityList) {
            incident.setStatus("ONGOING");
        }
        if (!incidentEntityList.isEmpty()){

            for (IncidentEntity incident : incidentEntityList) {
                int nbTruckToSend;
                System.out.println("fin du changement de status");
                // save pas forcément utile, normalement la transaction se charge seul, juste au cas où
                incidentRepository.saveOne(incident); // updateObject? truckRepository.updateObject(truck)

                // calcul du nombre de camion a envoyer
                if (incident.getSeverity() >= 8)
                    nbTruckToSend = 4;
                else if (incident.getSeverity() >= 5 && incident.getSeverity()<8)
                    nbTruckToSend = 3;
                else if (incident.getSeverity() >= 2 && incident.getSeverity()<4)
                    nbTruckToSend = 2;
                else
                    nbTruckToSend = 1;

                System.out.println("fin du nb de truck a envoyer " + nbTruckToSend);

                //Selection de la caserne la plus proche ayant un nombre de camion disponible suffisant

                Fire_stationsEntity nearestStation = new Fire_stationsEntity();
                boolean stationFound = false;
                while(!stationFound){
                    nearestStation = fireStationsEntityList.stream()
                            .min(Comparator.comparing(station ->
                                    this.calculDistance(station.getLat(),
                                            incident.getLat(), station.getLon(), incident.getLon())))
                            .orElseThrow(() -> new IllegalStateException("il n'existe aucunne station avec un nombre de camion suffisant"));
                    if (nearestStation.getTrucks_number() < nbTruckToSend)
                        fireStationsEntityList.remove(nearestStation);
                    else
                        stationFound = true;
                }

                System.out.println("fin calcul station la plus proche" + nearestStation.getLon() + "   " + nearestStation.getLat());

                //Retrait des camions de la station
                List<TruckEntity> trucks_list = iTruckJpaRepository.findtruckincident(nearestStation.getName());
                int i =0;
                for (TruckEntity truck : trucks_list){
                    if (i<nbTruckToSend){
                        TruckIncidentEntity truckIncidentEntity = new TruckIncidentEntity();
                        truckIncidentEntity.setId_truck(truck.getId());
                        truckIncidentEntity.setId_incident(incident.getId());
                        truckIncidentJPARepository.save(truckIncidentEntity);
                    }
                    i +=1;
                }
                nearestStation.setTrucks_number(nearestStation.getTrucks_number() - nbTruckToSend);

                double[] departureCoords = new double[2];
                double[] arrivalCoords = new double[2];
                departureCoords[1] = nearestStation.getLon();
                departureCoords[0] = nearestStation.getLat();
                arrivalCoords[1] = incident.getLon();
                arrivalCoords[0] = incident.getLat();

                System.out.println(departureCoords[0] + " lon: " + departureCoords[1] + " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + arrivalCoords[0] + " lon : "
                + arrivalCoords[1]);

                //Generation de l'itinéraire des camions
                MapboxDirections client = MapboxDirections.builder()
                        .accessToken("pk.eyJ1IjoiZ3RrMTg4IiwiYSI6ImNsZHN1czViOTBybTIzcmtncWQ4dGh2aW0ifQ.UUce1RE5mLKOHx-2zI_VWA")
                        .profile(DirectionsCriteria.PROFILE_DRIVING)
                        .annotations(List.of(DirectionsCriteria.ANNOTATION_DISTANCE, DirectionsCriteria.ANNOTATION_DURATION))
                        .origin(Point.fromLngLat(departureCoords[1], departureCoords[0]))
                        .destination(Point.fromLngLat(arrivalCoords[1], arrivalCoords[0]))
                        .steps(true)
                        .build();


                try {
                    Response<DirectionsResponse> response = client.executeCall();
                    if (response.isSuccessful()) {
                        System.out.println("response ok");
                        assert response.body() != null;
                        DirectionsRoute route = response.body().routes().get(0);
                        List<LegStep> steps = Objects.requireNonNull(route.legs()).get(0).steps();
                        //Recuperation de temps de trajet
                        double travelTime = route.duration().doubleValue();

                        //Remplissage de table management_incidents
                        ManagementIncidentEntity managementIncidentEntity = new ManagementIncidentEntity();
                        managementIncidentEntity.setId_incident(incident.getId());
                        managementIncidentEntity.setNb_trucks(nbTruckToSend);
                        managementIncidentEntity.setTravel(steps.toString());
                        managementIncidentEntity.setTravel_time(travelTime);
                        managementIncidentEntity.setOn_site(false);
                        managementIncidentRepository.saveObject(managementIncidentEntity);
                        // Attention ! Exception si on insert un incident déjà en base
                    }
                    else {
                        System.out.println("Failed to fetch directions: " + response.message());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private double calculDistance(double lat1, double lat2, double lon1, double lon2) {
        return Math.sqrt(Math.pow(lon1 - lon2, 2 + Math.pow(lat1 - lat2, 2)));
    }
}
