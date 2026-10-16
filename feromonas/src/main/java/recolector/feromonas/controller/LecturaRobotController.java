package recolector.feromonas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recolector.feromonas.dto.LecturaRobotDTO;
import recolector.feromonas.service.LecturaRobotService;

@RestController
@RequestMapping("/api/v1/telemetria")
@RequiredArgsConstructor
public class LecturaRobotController
{
    private final LecturaRobotService service;

    @PostMapping
    public ResponseEntity<Void> recibirLecturas(@Valid @RequestBody LecturaRobotDTO dto)
    {
        service.guardarLectura(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
