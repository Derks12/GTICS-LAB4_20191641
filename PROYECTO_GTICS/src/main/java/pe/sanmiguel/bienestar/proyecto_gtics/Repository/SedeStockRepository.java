package pe.sanmiguel.bienestar.proyecto_gtics.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.SedeStock;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.SedeStockId;

import java.util.List;

public interface SedeStockRepository extends JpaRepository<SedeStock, SedeStockId> {


}
