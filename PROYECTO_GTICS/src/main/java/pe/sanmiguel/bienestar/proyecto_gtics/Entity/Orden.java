package pe.sanmiguel.bienestar.proyecto_gtics.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "orden")
public class Orden {

    @Id
    private Integer idOrden;
    @Column
    private Date fechaIni;
    @Column
    private Date fechaFin;
    @Column
    private boolean noStock;
    @Column
    private boolean pagado;
    @Column
    private float precioTotal;
    @Column
    private Integer idFarmacista;
    @Column
    private Integer idPaciente;
    @Column
    private Integer idTipo;
    @Column
    private Integer idEstado;
    @Column
    private Integer idSede;
    @Column
    private Integer idDoctor;
}
