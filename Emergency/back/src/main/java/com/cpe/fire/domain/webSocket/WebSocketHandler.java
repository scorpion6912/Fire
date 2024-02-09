package com.cpe.fire.domain.webSocket;

import com.cpe.fire.domain.fire_stations.Fire_stations;
import com.cpe.fire.domain.incident.Incident;
import com.cpe.fire.domain.managementIncident.ManagementIncident;
import com.cpe.fire.repository.fire_stations.Fire_stationsRepository;
import com.cpe.fire.repository.incident.IncidentRepository;
import com.cpe.fire.repository.management_incidents.ManagementIncidentRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public final class WebSocketHandler extends TextWebSocketHandler {
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final IncidentRepository incidentRepository;
    private final Fire_stationsRepository fire_stationsRepository;
    private final ManagementIncidentRepository managementIncidentRepository;

    public WebSocketHandler(IncidentRepository incidentRepository, Fire_stationsRepository fire_stationsRepository, ManagementIncidentRepository managementIncidentRepository) {
        this.incidentRepository = incidentRepository;
        this.fire_stationsRepository = fire_stationsRepository;
        this.managementIncidentRepository = managementIncidentRepository;
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws IOException {
        System.out.println("Connexion établie");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS")
                .setPrettyPrinting()
                .create();

        final List<Fire_stations> fire_stations = fire_stationsRepository.findAll();
        final List<Incident> incidents = incidentRepository.findAllEntityONGOING();
        final List<ManagementIncident> managementIncidents = managementIncidentRepository.findAll();

        sessions.add(session);
        session.sendMessage(new TextMessage("FIRE_STATIONS"+gson.toJson(fire_stations)));
        session.sendMessage(new TextMessage("INCIDENTS"+gson.toJson(incidents)));
        session.sendMessage(new TextMessage("MANAGEMENT_INCIDENTS"+gson.toJson(managementIncidents)));

        System.out.println("Nombre de sessions : " + sessions.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Connexion fermée");
        sessions.remove(session);
        System.out.println("Nombre de sessions : " + sessions.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Récéption d'un message : " + message.getPayload());
    }
}