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
@Table(name = "medicamento")
public class Medicamento {

    @Id
    private Integer idMedicamento;
    @Column
    private String nombre;
    @Column
    private String unidad;
    @Column
    private String descripcion;
    @Column
    private String visibleSedes;
    @Column
    private String categoria;
    @Column
    private String componente;
    @Column
    private String precioCompra;
    @Column
    private String precioVenta;
    @Column
    private String recetable;
    @Column
    private String imagen;

}
