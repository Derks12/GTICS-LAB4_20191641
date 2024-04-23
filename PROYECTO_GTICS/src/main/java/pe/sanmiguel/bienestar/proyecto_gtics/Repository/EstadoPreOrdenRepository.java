package pe.sanmiguel.bienestar.proyecto_gtics.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.EstadoReposicion;

public interface EstadoPreOrdenRepository extends JpaRepository<EstadoReposicion,Integer> {
}
