package recolector.feromonas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import recolector.feromonas.dto.ComandoTanqueDTO;
import recolector.feromonas.dto.LecturaRobotDTO;
import recolector.feromonas.entity.AnalisisComportamiento;
import recolector.feromonas.entity.LecturaRobot;
import recolector.feromonas.enums.AccionesEnum;
import recolector.feromonas.factory.FabricaBiomimetica;
import recolector.feromonas.repository.AnalisisComportamientoRepository;
import recolector.feromonas.repository.lecturaRobotRepository;

@Service
@RequiredArgsConstructor
public class LecturaRobotService
{
    //Traemos el repositorio y la fabrica
    private final lecturaRobotRepository repository;
    private final AnalisisComportamientoRepository analisisRepository;
    private final FabricaBiomimetica fabrica;

    public ComandoTanqueDTO procesarLectura(LecturaRobotDTO dto)
    {
        // A. El service evalúa el entorno y decide la acción general
        AccionesEnum accion = evaluadorEntorno(dto);

        // B. La fábrica traduce la acción al tanque físico y químico exacto
        ComandoTanqueDTO ordenFisica = fabrica.obtenerComando(dto.especie(), accion);

        // C. Se guarda en la tabla 1 (Datos crudos de hardware)
        LecturaRobot lectura = new LecturaRobot();
        lectura.setRobotId(dto.robotId());
        lectura.setLecturaGas(dto.lecturaGas());
        lectura.setLecturaTemperatura(dto.lecturaTemperatura());
        lectura.setLecturaHumedad(dto.lecturaHumedad());
        repository.save(lectura);

        // D. Se guarda en la tabla 2 (Dataset para la IA)
        AnalisisComportamiento analisis = new AnalisisComportamiento();
        analisis.setRobotId(dto.robotId());
        analisis.setEspecie(dto.especie());
        analisis.setTemperaturaEscenario(dto.lecturaTemperatura());
        analisis.setGasEscenario(dto.lecturaGas());
        analisis.setHumedadEscenario(dto.lecturaHumedad());
        analisis.setAccionDecidida(accion);
        analisis.setCompuestoQuimicoUtilizado(ordenFisica.compuestoQuimico());

        //Se queda nulo por defecto el de exito
        analisisRepository.save(analisis);

        // E. Devolvemos la orden al robot postman
        return ordenFisica;
    }

    //Las condiciones generales
    private AccionesEnum evaluadorEntorno(LecturaRobotDTO dto)
    {
        if (dto.lecturaTemperatura() >= 35.0f || dto.lecturaGas() > 500.0f)
        {
            return AccionesEnum.ALERTA_PELIGRO;
        }
        // Clima ideal para buscar comida (Humedad alta)
        else if (dto.lecturaTemperatura() >= 24.0f && dto.lecturaTemperatura() <= 28.0f && dto.lecturaHumedad() >= 50)
        {
            return AccionesEnum.ATRACCION_ALIMENTO;
        }
        // Caminando normal
        else
        {
            return AccionesEnum.MONITOREO;
        }
    }
}
