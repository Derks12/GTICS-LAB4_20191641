package pe.sanmiguel.bienestar.proyecto_gtics.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer> {

    @Query("SELECT MAX(o.idOrden) FROM Orden o")
    Integer findLastOrdenId();

}
