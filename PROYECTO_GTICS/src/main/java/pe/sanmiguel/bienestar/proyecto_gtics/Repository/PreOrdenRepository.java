package pe.sanmiguel.bienestar.proyecto_gtics.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.PreOrden;

import java.util.List;

@Repository
public interface PreOrdenRepository extends JpaRepository<PreOrden, Integer> {

}
