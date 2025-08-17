package ${package}.web.controller;

import ${package}.api.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Health check controller
 */
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> health() {
        Map<String, Object> healthInfo = Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now(),
                "application", "${artifactId}",
                "version", "${version}"
        );

        return ResponseEntity.ok(ApiResponse.success("Application is healthy", healthInfo));
    }
}