package recolector.feromonas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import recolector.feromonas.enums.EspecieEnum;

//Es buena práctica usar record en los dto.
public record LecturaRobotDTO(

        @NotBlank(message = "El ID del robot es obligatorio")
        String robotId,

        @NotNull(message = "La especie no puede ser nula")
        EspecieEnum especie, //El robot nos dice que es (ej. "TENEBRIO_MOLITOR"

        @NotNull
        @Positive(message = "La lectura del gas debe ser positiva")

        float lecturaGas,

        @NotNull
        float lecturaTemperatura,

        @NotNull
        float lecturaHumedad
)
{}
