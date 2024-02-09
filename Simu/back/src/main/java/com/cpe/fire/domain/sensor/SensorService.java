package com.cpe.fire.domain.sensor;

import com.cpe.fire.repository.incident.IncidentEntity;
import com.cpe.fire.repository.incident.IncidentJpaRepository;
import com.cpe.fire.repository.incident.IncidentRepository;
import com.cpe.fire.repository.management_incidents.ManagementIncidentEntity;
import com.cpe.fire.repository.management_incidents.ManagementIncidentJpaRepository;
import com.cpe.fire.repository.sensor.ISensorJpaRepository;
import com.cpe.fire.repository.sensor.SensorEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.cpe.fire.repository.sensor.SensorRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.cpe.fire.domain.gps.GPSGenerator.generateRandomCoordinates;
import static com.cpe.fire.domain.gps.HttpPost.sendJsonPost;

@Service
public class SensorService {
    private final IncidentJpaRepository incidentJpaRepository;
    private final ISensorRepository iSensorRepository;
    private final IncidentRepository incidentRepository;
    private final ManagementIncidentJpaRepository managementIncidentJpaRepository;
    private final SensorRepository sensorRepository;
    private final ISensorJpaRepository iSensorJpaRepository;

    public SensorService(IncidentJpaRepository incidentJpaRepository, ISensorRepository iSensorRepository, IncidentRepository incidentRepository, ManagementIncidentJpaRepository managementIncidentJpaRepository, SensorRepository sensorRepository, ISensorJpaRepository iSensorJpaRepository) {
        this.incidentJpaRepository = incidentJpaRepository;
        this.iSensorRepository = iSensorRepository;
        this.incidentRepository = incidentRepository;
        this.managementIncidentJpaRepository = managementIncidentJpaRepository;
        this.sensorRepository = sensorRepository;
        this.iSensorJpaRepository = iSensorJpaRepository;
    }

    public void sensorCreation (String severity) throws InterruptedException {
        List<SensorEntity> sensorList = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS")
                .setPrettyPrinting()
                .create();
        int minValue = 0;
        int maxValue = 100;
        double exponent = 10-Integer.parseInt(severity);
        for (long i = 1; i < 60; i++) {
            double[] gps = generateRandomCoordinates();
            SensorEntity sensor = new SensorEntity();
            sensor.setId(i);
            sensor.setLat(gps[0]);
            sensor.setLon(gps[1]);
            sensor.setTime(new Timestamp(System.currentTimeMillis()));
            Random rand = new Random();
            double randomValue = rand.nextDouble() * 0.8;
            int generatedNumber = minValue + (int) (Math.pow(randomValue, exponent) * (maxValue - minValue + 1));
            sensor.setValue(generatedNumber);
            sensorList.add(sensor);
        }
        System.out.println(gson.toJson(sensorList));
        sendJsonPost(gson.toJson(sensorList));
        while (true){
            Thread.sleep(1800000);
            List<SensorEntity> sensorList2 = new ArrayList<>();
            for (SensorEntity sensor:sensorList){
                if (sensor.getValue()>10){
                    sensor.setTime(new Timestamp(System.currentTimeMillis()));
                    sensorList2.add(sensor);
                }else {
                    Random rand = new Random();
                    double randomValue = rand.nextDouble() * 0.8;
                    int generatedNumber = minValue + (int) (Math.pow(randomValue, exponent) * (maxValue - minValue + 1));
                    sensor.setTime(new Timestamp(System.currentTimeMillis()));
                    sensor.setValue(generatedNumber);
                    sensorList2.add(sensor);
                }
            }
            System.out.println(gson.toJson(sensorList));
            sendJsonPost(gson.toJson(sensorList2));
        }
    }
    public void strat_simu() throws InterruptedException {
        simu();
    }

    public void simu() throws InterruptedException {
        List<IncidentEntity> incidentOngoing = incidentRepository.findAllEntityOngoing();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS")
                .setPrettyPrinting()
                .create();
        List<ManagementIncidentEntity> incidentManagement = managementIncidentJpaRepository.findAll();
        long time= 0;
        while (!incidentOngoing.isEmpty()){
            for(IncidentEntity incidentEntity:incidentOngoing) {
                long id = incidentEntity.getId();
                ManagementIncidentEntity managementIncidentEntity1 = null;
                for (ManagementIncidentEntity managementIncidentEntity:incidentManagement) {
                    if (managementIncidentEntity.getId_incident() == id){
                        managementIncidentEntity1 = managementIncidentEntity;
                    }
                }
                System.out.println(managementIncidentEntity1.getTravel_time());
                if (time > managementIncidentEntity1.getTravel_time()) {
                    System.out.println("ça brule pu");
                    List<SensorEntity> sensor_list3 = iSensorJpaRepository.findAll();
                    List<SensorEntity> sensorList4 = new ArrayList<>();
                    for (SensorEntity sensor:sensor_list3){
                        double sev = sensor.getValue();
                        if (sensor.getLat() == incidentEntity.getLat()
                                && sensor.getLon() == incidentEntity.getLon()) {
                            sensor.setValue(sev - 2);
                            sensorList4.add(sensor);
                            sendJsonPost(gson.toJson(sensorList4));
                        }
                        if (sev < 0.1) {
                        System.out.println("update");
                        incidentEntity.setStatus("FINISHED");
                        incidentManagement.remove(managementIncidentEntity1);
                        }
                    }
                } else {
                    Random rand = new Random();
                    int j = rand.nextInt(100) + 1;
                    if (j < 10) {
                        System.out.println("ça brule");
                        List<SensorEntity> sensor_list = iSensorJpaRepository.findAll();
                        List<SensorEntity> sensorList2 = new ArrayList<>();
                        for (SensorEntity sensor:sensor_list){
                            if (sensor.getLat() == incidentEntity.getLat()
                                    && sensor.getLon() == incidentEntity.getLon()){
                                System.out.println("pute");
                                double sev = incidentEntity.getSeverity();
                                sensor.setValue(sev+10);
                                sensorList2.add(sensor);
                                sendJsonPost(gson.toJson(sensorList2));
                            }
                        }
                    }
                }
                incidentJpaRepository.save(incidentEntity);
            }
            Thread.sleep(2000);
            time+=2;
            System.out.println(time);
            incidentOngoing = incidentRepository.findAllEntityOngoing();
        }

    }
    public List<Sensor> getAllSensor(){
        return sensorRepository.findAll();
    }
}
