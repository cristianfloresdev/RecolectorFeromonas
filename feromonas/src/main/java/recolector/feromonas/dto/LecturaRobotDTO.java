package recolector.feromonas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

//Es buena práctica usar record en los dto.
public record LecturaRobotDTO(
        @NotNull
        @Positive(message = "La lectura del gas debe ser positiva")

        float lecturaGas,

        @NotNull
        float lecturaTemperatura,

        @NotNull
        float lecturaHumedad
)
{}
