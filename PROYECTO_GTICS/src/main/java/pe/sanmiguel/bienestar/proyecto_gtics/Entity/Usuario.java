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
@Table(name = "usuario")
public class Usuario {
    @Id
    private Integer idUsuario;
    @Column
    private Integer idRol;
    @Column
    private String correo;
    @Column
    private String contrasena;
    @Column
    private String nombres;
    @Column
    private String apellidos;
    @Column
    private String dni;
    @Column
    private String direccion;
    @Column
    private String distrito;
    @Column
    private String seguro;
    @Column
    private boolean aceptado;
    @Column
    private boolean baneado;
}
