package recolector.feromonas.factory;

import org.springframework.stereotype.Component;
import recolector.feromonas.dto.ComandoTanqueDTO;
import recolector.feromonas.enums.AccionesEnum;
import recolector.feromonas.enums.EspecieEnum;

@Component
public class FabricaBiomimetica
{
    public ComandoTanqueDTO obtenerComando(EspecieEnum especie, AccionesEnum accion)
    {
        return switch (especie)
        {
            case TENEBRIO_ESCARABAJO -> obtenerComandoTenebrio(accion);

            default -> new ComandoTanqueDTO("desconocido", 0);
        };
    }

    private ComandoTanqueDTO obtenerComandoTenebrio(AccionesEnum accion)
    {
        return switch (accion)
        {
            case ATRACCION_ALIMENTO -> new  ComandoTanqueDTO("EXTRACTO_LEVADURA", 1);
            case AGRUPAR_RASTRO ->  new  ComandoTanqueDTO("ACIDO_ACÉTICO", 2);
            case ALERTA_PELIGRO ->  new  ComandoTanqueDTO("METIL_NONANOL", 3);
            case MONITOREO ->  new  ComandoTanqueDTO("Análisis", 0);
        };
    }
}
