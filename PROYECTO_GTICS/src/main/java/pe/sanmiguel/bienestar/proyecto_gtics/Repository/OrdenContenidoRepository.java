package pe.sanmiguel.bienestar.proyecto_gtics.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.OrdenContenido;

@Repository
public interface OrdenContenidoRepository extends JpaRepository<OrdenContenido, Integer> {
}