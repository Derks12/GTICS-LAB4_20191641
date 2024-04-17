package pe.sanmiguel.bienestar.proyecto_gtics.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orden_contenido")
public class OrdenContenido {

    @Id
    private Integer idOrden;
    @Id
    private Integer idMedicamento;
    @Column
    private int cantidad;

}
