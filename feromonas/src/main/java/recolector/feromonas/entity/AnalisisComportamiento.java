package recolector.feromonas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recolector.feromonas.enums.AccionesEnum;
import recolector.feromonas.enums.EspecieEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "análisis_comportamiento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalisisComportamiento
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "robot_id", nullable = false)
    private String robotId;

    @Enumerated(EnumType.STRING)
    @Column(name = "especie", nullable = false)
    private EspecieEnum especie;

    @Column(name = "temperatura_escenario")
    private float temperaturaEscenario;

    @Column(name = "gas_escenario")
    private float gasEscenario;

    @Column(name = "humedad_escenario")
    private float humedadEscenario;

    //Lo que el servidor decidió hacer
    @Enumerated(EnumType.STRING)
    @Column(name = "acción_decidida")
    private AccionesEnum accionDecidida;

    @Column(name = "compuesto_quimico_utilizado")
    private String  compuestoQuimicoUtilizado;

    //El feedback para la ia
    @Column(name = "éxito_comprobado")
    private Boolean exitoComprobado;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void prePersist()
    {
        this.fechaRegistro = LocalDateTime.now();
    }
}
