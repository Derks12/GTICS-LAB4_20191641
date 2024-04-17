package pe.sanmiguel.bienestar.proyecto_gtics;

import java.sql.Date;
import java.time.LocalDateTime;

public class CurrentTimeSQL {

    public static Date getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        return Date.valueOf(now.toLocalDate());
    }
}
