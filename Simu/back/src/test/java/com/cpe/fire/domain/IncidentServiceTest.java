package com.cpe.fire.domain;

import com.cpe.fire.domain.incident.IIncidentRepository;
import com.cpe.fire.domain.incident.IncidentService;
import com.cpe.fire.repository.incident.IncidentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest {

    @InjectMocks
    public IncidentService incidentService;

    @Mock
    public IIncidentRepository iIncidentRepository;

    @Test
    public void generateIncidentShouldTransformStringDataIntoIncidentEntity() {
        //given
        String dataReceived = """
                [
                    {
                        "id": 10,
                        "lon": 1,
                        "lat": 1.258,
                        "value": 98,
                        "timestamp": "2023-12-21 13:09:44.000000"
                    },
                    {
                        "id": 12,
                        "lon": 100,
                        "lat": 1.258,
                        "value": 98,
                        "timestamp": "2023-12-21 13:09:44.000000"
                    },
                    {
                        "id": 15,
                        "lon": 150,
                        "lat": 1.258,
                        "value": 98,
                        "timestamp": "2023-12-21 13:09:44.000000"
                    }
                ]
                """;
        //when


        //then
    }
}
