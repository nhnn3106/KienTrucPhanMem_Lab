package fit.iuh.se.availability.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @Value("${server.port:8080}")
    private String serverPort;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("status", "UP");
            response.put("timestamp", LocalDateTime.now());
            response.put("port", serverPort);
            response.put("hostname", InetAddress.getLocalHost().getHostName());
            response.put("ip", InetAddress.getLocalHost().getHostAddress());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "DOWN");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> home() {
        Map<String, String> response = new HashMap<>();
        try {
            response.put("message", "Welcome to Availability Service");
            response.put("server", "Server running on port " + serverPort);
            response.put("hostname", InetAddress.getLocalHost().getHostName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/api/data")
    public ResponseEntity<Map<String, Object>> getData() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("message", "Data from server on port " + serverPort);
            response.put("timestamp", LocalDateTime.now());
            response.put("hostname", InetAddress.getLocalHost().getHostName());
            response.put("data", new String[]{"Item 1", "Item 2", "Item 3"});
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}

