package pe.sanmiguel.bienestar.proyecto_gtics.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Orden;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    @Query(value = "SELECT * FROM orden WHERE idTipo = (SELECT id FROM tipo_orden WHERE nombre = 'WEB')", nativeQuery = true)
    List<Orden> findAllOrdenesWeb();
    @Query(value = "SELECT * FROM orden WHERE idTipo = (SELECT id FROM tipo_orden WHERE nombre = 'PREORDEN')", nativeQuery = true)
    List<Orden> findAllPreOrdenes();
    @Query(value="SELECT MAX(orden.id) FROM Orden orden", nativeQuery = true)
    Integer findLastOrdenId();

    Orden findByIdOrdenAndTipoOrden(Integer idOrden, Integer tipoOrden);


    /*@Query("SELECT o FROM Orden o WHERE o.idSede = :idSede")
    List<Orden> findBySedeId(Integer idSede);*/


}
