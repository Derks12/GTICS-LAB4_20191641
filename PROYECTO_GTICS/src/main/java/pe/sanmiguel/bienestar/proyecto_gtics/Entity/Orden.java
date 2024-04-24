package pe.sanmiguel.bienestar.proyecto_gtics.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "orden")
public class Orden {
    @Id
    @Column(name="id")
    private Integer idOrden;
    @Column
    private String tracking;
    @Column
    private LocalDate fechaIni;
    @Column
    private LocalDate fechaFin;
    @Column
    private float precioTotal;
    @Column
    private Integer idFarmacista;
    @ManyToOne
    @JoinColumn(name="idPaciente")
    private Usuario paciente;
    @ManyToOne
    @JoinColumn(name = "idTipo")
    private TipoOrden tipoOrden;
    @ManyToOne
    @JoinColumn(name="idEstado")
    private EstadoOrden estadoOrden;
    @ManyToOne
    @JoinColumn(name="idSede")
    private Sede sede;
    @ManyToOne
    @JoinColumn(name = "idDoctor")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "estado_preorden")
    private EstadoPreOrden estadoPreOrden;
}
