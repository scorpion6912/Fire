package com.cpe.fire.domain.incident;

import com.cpe.fire.domain.sensor.ISensorRepository;
import com.cpe.fire.domain.sensor.Sensor;
import com.cpe.fire.repository.incident.IncidentEntity;
import com.cpe.fire.repository.incident.IncidentEntityMapper;
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

    public IncidentService(IIncidentRepository iIncidentRepository,
                           IncidentEntityMapper incidentEntityMapper,
                           ISensorRepository iSensorRepository,
                           SensorEntityMapper sensorEntityMapper)
    {
        this.iIncidentRepository = iIncidentRepository;
        this.incidentEntityMapper = incidentEntityMapper;
        this.iSensorRepository = iSensorRepository;
        this.sensorEntityMapper = sensorEntityMapper;
    }

    public List<Incident> getAllIncident() {
        return iIncidentRepository.findAll();
    }

    public void insertIncident (String data) { // test unitaire a faire
        // TODO: transformer les datas recu en json puis en Object Incident
        //  et les envoyer au repository pour inserer dans la DB
        data = """
                [
                    {
                        "id": 10,
                        "lon": 1,
                        "lat": 1.258,
                        "value": 98,
                        "time": "2023-12-21 13:09:44"
                    },
                    {
                        "id": 12,
                        "lon": 100,
                        "lat": 1.258,
                        "value": 98,
                        "time": "2023-12-21 13:09:44"
                    },
                    {
                        "id": 15,
                        "lon": 150,
                        "lat": 1.258,
                        "value": 98,
                        "time": "2023-12-21 13:09:44"
                    }
                ]
                """;

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
        // si on arrive ici, 2e envoie, donc on veut juste verifier que la liste a bien le même
        // nombre de capteur que celle de la BDD (éviter les capteurs frauduleux en + ou en -)
        if (sensorListToInsert.size() == currentList.size()) {
            System.out.println(sensorListToInsert.size());
            System.out.println("list envoyé pour traitement en incident" + sensorListToInsert);
            List<IncidentEntity> incidentEntityList = generateIncident(sensorListToInsert);
            iIncidentRepository.insertIncident(incidentEntityList);
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
