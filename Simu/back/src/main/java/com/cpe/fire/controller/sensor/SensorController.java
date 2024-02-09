package com.cpe.fire.controller.sensor;

import com.cpe.fire.domain.sensor.SensorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/sensor")
@CrossOrigin(origins = "*")
public class SensorController {
    private final SensorDtoMapper sensorDtoMapper;

    private final SensorService sensorService;

    public SensorController(SensorDtoMapper sensorDtoMapper, SensorService sensorService) {
        this.sensorDtoMapper = sensorDtoMapper;
        this.sensorService = sensorService;
    }

    @GetMapping("/start")
    public void setUpSensor(String severity) throws IOException, InterruptedException {
        System.out.println(severity);
        sensorService.sensorCreation(severity);
    }

    @GetMapping
    public List<SensorDTO> getAllSensor(){
        return sensorService.getAllSensor().stream().map(
                sensorDtoMapper::sensorToSensorDto
        ).toList();
    }

    @GetMapping("/starti")
    public void start_sim(String sev) throws InterruptedException {
        System.out.println(sev);
        sensorService.strat_simu();
    }
}