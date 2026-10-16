package recolector.feromonas.dto;

public record ComandoTanqueDTO(
        String compuestoQuimico,  //Ejemplo Acido Salicílico
        Integer numeroTanque //Ejemplo 0 es igual a nada 2 es el tanque secundario
)
{ }
