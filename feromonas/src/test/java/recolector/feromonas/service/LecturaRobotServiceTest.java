package recolector.feromonas.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import recolector.feromonas.dto.ComandoTanqueDTO;
import recolector.feromonas.dto.LecturaRobotDTO;
import recolector.feromonas.entity.AnalisisComportamiento;
import recolector.feromonas.entity.LecturaRobot;
import recolector.feromonas.enums.AccionesEnum;
import recolector.feromonas.enums.EspecieEnum;
import recolector.feromonas.factory.FabricaBiomimetica;
import recolector.feromonas.repository.AnalisisComportamientoRepository;
import recolector.feromonas.repository.LecturaRobotRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LecturaRobotServiceTest
{

    // Simulamos la Base de Datos en memoria
    @Mock
    private LecturaRobotRepository telemetriaRepository;

    @Mock
    private AnalisisComportamientoRepository analisisRepository;

    @Mock
    private FabricaBiomimetica fabrica;

    // Inyectamos los simuladores en tu servicio real
    @InjectMocks
    private LecturaRobotService service;

    @BeforeEach
    void setUp() {
        // Le decimos a los repositorios que no hagan nada real, solo devuelvan un objeto vacío
        lenient().when(telemetriaRepository.save(any(LecturaRobot.class))).thenReturn(new LecturaRobot());
        lenient().when(analisisRepository.save(any(AnalisisComportamiento.class))).thenReturn(new AnalisisComportamiento());
    }

    @Test
    void debeDevolverMetilNonanolEnPeligroPorCalorYGas() {
        // 1. Arrange: Simulamos una lectura extrema
        LecturaRobotDTO dtoMortal = new LecturaRobotDTO(
                "ROBOT-01", EspecieEnum.TENEBRIO_ESCARABAJO, 150.0f, 40.0f, 30
        );

        ComandoTanqueDTO comandoEsperado = new ComandoTanqueDTO("METIL_NONANOL", 2);

        when(fabrica.obtenerComando(EspecieEnum.TENEBRIO_ESCARABAJO, AccionesEnum.ALERTA_PELIGRO))
                .thenReturn(comandoEsperado);

        // 2. Act: Ejecutamos el servicio
        ComandoTanqueDTO resultado = service.procesarLectura(dtoMortal);

        // 3. Assert: Verificamos que el cerebro tomó la decisión correcta
        assertEquals("METIL_NONANOL", resultado.compuestoQuimico());

        // Verificamos que intentó guardar en las dos tablas
        verify(telemetriaRepository, times(1)).save(any(LecturaRobot.class));
        verify(analisisRepository, times(1)).save(any(AnalisisComportamiento.class));
    }

    @Test
    void debeDevolverLevaduraEnClimaIdeal() {
        // 1. Arrange: Clima perfecto
        LecturaRobotDTO dtoIdeal = new LecturaRobotDTO(
                "ROBOT-01", EspecieEnum.TENEBRIO_ESCARABAJO, 15.0f, 26.0f, 65
        );

        ComandoTanqueDTO comandoEsperado = new ComandoTanqueDTO("EXTRACTO_LEVADURA", 1);

        when(fabrica.obtenerComando(EspecieEnum.TENEBRIO_ESCARABAJO, AccionesEnum.ATRACCION_ALIMENTO))
                .thenReturn(comandoEsperado);

        // 2. Act
        ComandoTanqueDTO resultado = service.procesarLectura(dtoIdeal);

        // 3. Assert
        assertEquals("EXTRACTO_LEVADURA", resultado.compuestoQuimico());
    }
}