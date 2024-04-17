package pe.sanmiguel.bienestar.proyecto_gtics.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orden_contenido")
public class OrdenContenido {

    @Id
    private Integer idEntrada;
    @Column
    private Integer idOrden;
    @Column
    private Integer idMedicamento;
    @Column
    private int cantidad;

}
