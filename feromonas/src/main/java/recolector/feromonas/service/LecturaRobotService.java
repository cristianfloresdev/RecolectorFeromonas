package recolector.feromonas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import recolector.feromonas.dto.LecturaRobotDTO;
import recolector.feromonas.entity.LecturaRobot;
import recolector.feromonas.repository.lecturaRobotRepository;

@Service
@RequiredArgsConstructor
public class LecturaRobotService
{
    private final lecturaRobotRepository repository;

    public LecturaRobot guardarLectura(LecturaRobotDTO dto)
    {
        LecturaRobot entidad = new LecturaRobot();
        entidad.setLecturaGas(dto.lecturaGas());
        entidad.setLecturaTemperatura(dto.lecturaTemperatura());
        entidad.setLecturaHumedad(dto.lecturaHumedad());

        return repository.save(entidad);
    }
}
