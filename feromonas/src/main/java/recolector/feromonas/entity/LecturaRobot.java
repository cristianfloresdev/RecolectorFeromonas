package recolector.feromonas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lectura_general")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturaRobot
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lectura_gas", nullable = false)
    private float lecturaGas;

    @Column(name = "lectura_temperatura", nullable = false)
    private float lecturaTemperatura;

    @Column(name = "lectura_humedad", nullable = false)
    private float lecturaHumedad;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void prePersist()
    {
        this.fechaRegistro = LocalDateTime.now();
    }
}
