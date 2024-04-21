package pe.sanmiguel.bienestar.proyecto_gtics.Entity;

import jakarta.persistence.*;
import jdk.jfr.SettingDefinition;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name= "preorden")
public class PreOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPreorden;
    @ManyToOne
    @JoinColumn(name="idOrden")
    private Orden orden;
    @Column
    private String tracking;
    @Column
    private Date fechaIni;
    @Column
    private Date fechaFin;
    @Column
    private boolean pagado;
    @Column
    private float precioTotal;
    @Column
    private int idFarmacista;

    @ManyToOne
    @JoinColumn(name = "idPaciente")
    private Usuario paciente;
    @ManyToOne
    @JoinColumn(name = "idEstado")
    private EstadoPreOrden estadoPreOrden;
    @ManyToOne
    @JoinColumn(name="idSede")
    private Sede sede;
}
