package com.cpe.fire.domain.incident;

import com.cpe.fire.domain.sensor.ISensorRepository;
import com.cpe.fire.domain.sensor.Sensor;
import com.cpe.fire.domain.truck.TruckService;
import com.cpe.fire.repository.incident.IncidentEntity;
import com.cpe.fire.repository.incident.IncidentEntityMapper;
import com.cpe.fire.repository.sensor.ISensorJpaRepository;
import com.cpe.fire.repository.sensor.SensorEntity;
import com.cpe.fire.repository.sensor.SensorEntityMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class IncidentService {

    private final IIncidentRepository iIncidentRepository;

    private final IncidentEntityMapper incidentEntityMapper;

    private final ISensorRepository iSensorRepository;

    private final SensorEntityMapper sensorEntityMapper;

    private final TruckService truckService;

    private final ISensorJpaRepository iSensorJpaRepository;

    public IncidentService(IIncidentRepository iIncidentRepository,
                           IncidentEntityMapper incidentEntityMapper,
                           ISensorRepository iSensorRepository,
                           SensorEntityMapper sensorEntityMapper,
                           TruckService truckService, ISensorJpaRepository iSensorJpaRepository)
    {
        this.iIncidentRepository = iIncidentRepository;
        this.incidentEntityMapper = incidentEntityMapper;
        this.iSensorRepository = iSensorRepository;
        this.sensorEntityMapper = sensorEntityMapper;
        this.truckService = truckService;
        this.iSensorJpaRepository = iSensorJpaRepository;
    }

    public List<Incident> getAllIncident() {
        return iIncidentRepository.findAll();
    }
    public List<Incident> getAllEntityONGOING() {
        return iIncidentRepository.findAllEntityONGOING();
    }

    public void insertIncident (String data) { // test unitaire a faire
        // TODO: transformer les datas recu en json puis en Object Incident
        //  et les envoyer au repository pour inserer dans la DB
        /*data = """
                [
                    {
                        "id": 10,
                        "lon": 4.3,
                        "lat": 45.3,
                        "value": 98,
                        "time": "2023-12-21 13:09:44"
                    },
                    {
                        "id": 12,
                        "lon": 4.5,
                        "lat": 45.5,
                        "value": 98,
                        "time": "2023-12-21 13:09:44"
                    },
                    {
                        "id": 15,
                        "lon": 5,
                        "lat": 45,
                        "value": 98,
                        "time": "2023-12-21 13:09:44"
                    }
                ]
                """;*/

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        List<Sensor> sensorListToInsert = List.of(gson.fromJson(data, Sensor[].class)); //list recu

        System.out.println("sensor to insert" + sensorListToInsert);

        List<Sensor> currentList = iSensorRepository.findAll(); // list des sensors en BD

        System.out.println("current list" + currentList);

        if (currentList.isEmpty()) { // cas table sensor vide donc 1er envoie
            System.out.println("coucouc je suis dans le sensor empty");
            List<SensorEntity> sensorList = sensorListToInsert.stream()
                    .map(sensorEntityMapper::sensorToSensorEntity).toList();
            iSensorRepository.insertSensor(sensorList);
        }

        // verif id et position des deux listes, si égales, pas d'incident a créer => update la table sensors
        boolean sameSensor = false;
        if (sensorListToInsert.size() == currentList.size()) {
            int i = 0;
            for (Sensor sensor : sensorListToInsert) {
                if (sensor.id() == currentList.get(i).id() && sensor.lat() == currentList.get(i).lat()
                        && sensor.lon() == currentList.get(i).lon()){
                    sameSensor = true;
                }
                else
                    System.out.println("not same sensor");
            }
        }
        else if (sensorListToInsert.size()==1){ //insert updated valeur of sensor in DB
            List<SensorEntity> sentorToUpdate = sensorListToInsert.stream()
                    .map(sensorEntityMapper::sensorToSensorEntity).toList();
            iSensorJpaRepository.saveAll(sentorToUpdate);
        }

        List<SensorEntity> sensorEntityList = sensorListToInsert.stream().map(
                sensorEntityMapper::sensorToSensorEntity
        ).toList();
        iSensorJpaRepository.saveAll(sensorEntityList); //Update sensors

        if (!sameSensor) {
            System.out.println("list envoyé pour traitement en incident" + sensorListToInsert);
            List<IncidentEntity> incidentEntityList = generateIncident(sensorListToInsert);
            iIncidentRepository.insertIncident(incidentEntityList);
            truckService.manageNewIncident();
        }
    }

    private List<IncidentEntity> generateIncident(List<Sensor> sensorList) {
        List<IncidentEntity> incidentEntityList = new ArrayList<>();

        // création des incidents
        for (Sensor sensor : sensorList) {
            double severityDouble = sensor.value()/10;
            int severity = (int)severityDouble;
            Date date = Date.valueOf(sensor.time().toLocalDateTime().toLocalDate());
            incidentEntityList.add(incidentEntityMapper.incidentToIncidentEntity(
                new Incident(null, "fire", sensor.lon(),
                        sensor.lat(), severity, date, null, "TODO"))
            );
        }

        //Remove les incidents ayant une severité de 0 => Valeur normal
        incidentEntityList.removeIf(incidentEntity -> incidentEntity.getSeverity() == 0);
        return incidentEntityList;
    }

    public void updateIncidentManagement (String data) {
        // TODO: transformer les datas recu en json puis en Object Incident
        //  et les envoyer au repository pour update la DB

        iIncidentRepository.updateIncidentManagement(null);
    }
}
