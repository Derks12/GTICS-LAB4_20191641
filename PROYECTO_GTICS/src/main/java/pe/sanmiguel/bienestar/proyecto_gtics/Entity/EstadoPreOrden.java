package pe.sanmiguel.bienestar.proyecto_gtics.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "estado_preorden")
public class EstadoPreOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstado;
    @Column
    private String nombre;
}
