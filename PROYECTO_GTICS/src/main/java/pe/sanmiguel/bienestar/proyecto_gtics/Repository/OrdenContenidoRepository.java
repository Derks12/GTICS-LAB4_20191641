package pe.sanmiguel.bienestar.proyecto_gtics.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.OrdenContenido;

import java.util.ArrayList;

@Repository
public interface OrdenContenidoRepository extends JpaRepository<OrdenContenido, Integer> {
    /*ArrayList<OrdenContenido> findAllByIdOrden(Integer idOrden);

    @Query("SELECT MAX(oc.idEntrada) FROM OrdenContenido oc")
    Integer findLastOrdenContenidoId();*/
}
