package pe.sanmiguel.bienestar.proyecto_gtics.Controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.sanmiguel.bienestar.proyecto_gtics.CurrentTimeSQL;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.*;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class FarmacistaController {


    /* Repositorios */
    final UsuarioRepository usuarioRepository;
    final SedeRepository sedeRepository;
    final SedeStockRepository sedeStockRepository;
    final MedicamentoRepository medicamentoRepository;
    final OrdenRepository ordenRepository;
    final OrdenContenidoRepository ordenContenidoRepository;
    final ReposicionRepository reposicionRepository;
    final EstadoPreOrdenRepository estadoPreOrdenRepository;
    final DoctorRepository doctorRepository;

    public FarmacistaController(UsuarioRepository usuarioRepository, SedeRepository sedeRepository, SedeStockRepository sedeStockRepository, MedicamentoRepository medicamentoRepository, OrdenRepository ordenRepository, OrdenContenidoRepository ordenContenidoRepository, ReposicionRepository reposicionRepository, EstadoPreOrdenRepository estadoPreOrdenRepository, DoctorRepository doctorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sedeRepository = sedeRepository;
        this.sedeStockRepository = sedeStockRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.ordenRepository = ordenRepository;
        this.ordenContenidoRepository = ordenContenidoRepository;
        this.reposicionRepository  =reposicionRepository;
        this.estadoPreOrdenRepository = estadoPreOrdenRepository;
        this.doctorRepository = doctorRepository;
    }

    /* Repositorios */


    /* Variables Internas */

    Sede sedeSession;
    List<Medicamento> medicamentosSeleccionados = new ArrayList<>();
    List<String> listaCantidades = new ArrayList<>();

    List<Medicamento> medicamentosSinStock = new ArrayList<>();
    List<Medicamento> medicamentosConStock = new ArrayList<>();
    List<String> cantidadesFaltantes = new ArrayList<>();
    List<String> cantidadesExistentes = new ArrayList<>();

    Integer idVerOrdenCreada;
    Integer idVerPreOrdenCreada;

    /* Variables Internas */



    @GetMapping("/farmacista")
    public String farmacistaInicio(Model model) {
        List<Medicamento> listaMedicamentos = medicamentoRepository.findAll();
        sedeSession = sedeRepository.getSedeByIdSede(1);
        model.addAttribute("sedeSession", sedeSession);
        model.addAttribute("listaMedicamentos", listaMedicamentos);
        return "/farmacista/inicio";
    }

    @PostMapping("/farmacista/continuar_compra")
    public String fillContentOrder(@RequestParam("listaIds") List<String> listaSelectedIds){
        if (!listaSelectedIds.isEmpty()){
            medicamentosSeleccionados = getMedicamentosFromLista(listaSelectedIds);
            listaCantidades = getCantidadesFromLista(listaSelectedIds);
            return "redirect:/farmacista/formulario_paciente";
        } else {
            return "redirect:/farmacista";
        }
    }

    @GetMapping("/farmacista/formulario_paciente")
    public String formPacienteData(Model model) {
        model.addAttribute("medicamentosSeleccionados", medicamentosSeleccionados);
        model.addAttribute("listaCantidades", listaCantidades);
        return "/farmacista/formulario_paciente";
    }

    @PostMapping("/farmacista/finalizar_compra")
    public String createOrdenVenta(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "lastname") String lastname,
                                   @RequestParam(value = "dni") String dni,
                                   @RequestParam(value = "distrito") String distrito,
                                   @RequestParam(value = "direccion") String direccion,
                                   @RequestParam(value = "doctor") String doctor,
                                   @RequestParam(value = "seguro") String seguro,
                                   @RequestParam(value = "correo") String correo,
                                   @RequestParam(value = "telefono") String telefono,

                                   @RequestParam(value = "listaIds") List<String> listaSelectedIds,
                                   @RequestParam(value = "priceTotal") String priceTotal) {

        medicamentosSeleccionados = getMedicamentosFromLista(listaSelectedIds);
        listaCantidades = getCantidadesFromLista(listaSelectedIds);

        verificationStock verificationStock = new verificationStock(medicamentosSeleccionados, listaCantidades);

        if (verificationStock.getMedicamentosSinStock().isEmpty()){

            Orden newOrden = new Orden();

            newOrden.setIdOrden(ordenRepository.findLastOrdenId() + 1);
            newOrden.setFechaIni(CurrentTimeSQL.getCurrentDate());
            newOrden.setPrecioTotal(Float.parseFloat(priceTotal));
            //Id conocido porque no hay session
            newOrden.setIdFarmacista(7);
            newOrden.setTipoOrden(1);
            newOrden.setEstadoOrden(8);
            newOrden.setSede(sedeSession);

            if (!doctor.equals("sin-doctor")) {
                newOrden.setDoctor(doctorRepository.getByIdDoctor(Integer.valueOf(doctor)));
            }

            int i = 0;
            for (Medicamento med : medicamentosSeleccionados){

                OrdenContenido contenido = new OrdenContenido();
                contenido.setIdOrden(newOrden);
                contenido.setIdMedicamento(med);
                contenido.setCantidad(Integer.parseInt(listaCantidades.get(i)));
                ordenContenidoRepository.save(contenido);
                i++;
            }

            return "redirect:/farmacista/ver_orden_venta";
        } else {

            this.medicamentosSinStock = verificationStock.getMedicamentosSinStock();
            this.medicamentosConStock = verificationStock.getMedicamentosConStock();
            this.cantidadesFaltantes = verificationStock.getCantidadesFaltantes();
            this.cantidadesExistentes = verificationStock.getCantidadesExistentes();

            return "redirect:/farmacista/crear_preorden";
        }
    }


    @GetMapping("/farmacista/crear_preorden")
    public String createPreOrden(Model model) {

        model.addAttribute("medicamentosSinStock", medicamentosSinStock);
        model.addAttribute("medicamentosConStock", medicamentosConStock);
        model.addAttribute("cantidadesFaltantes", cantidadesFaltantes);
        model.addAttribute("cantidadesExistentes", cantidadesExistentes);

        return "/farmacista/crear_preorden";
    }

    @GetMapping("/farmacista/ver_pre_orden")
    public String verPreOrden(Model model) {

        Optional<Orden> preOrdenOptional = ordenRepository.findById(idVerPreOrdenCreada);

        if (preOrdenOptional.isPresent()){
            Orden preOrdenComprobada = preOrdenOptional.get();
            model.addAttribute("preOrden",preOrdenComprobada);
            return "/farmacista/ver_pre_orden";
        } else {

            return "/farmacista/errorPages/no_existe_orden";
        }
    }


    @GetMapping("/farmacista/ver_orden_venta")
    public String verOrdenVenta(Model model) {

        Optional<Orden> ordenOptional = ordenRepository.findById(idVerOrdenCreada);

        if (ordenOptional.isPresent()){
            Orden ordenComprobada = ordenOptional.get();
            model.addAttribute("orden",ordenComprobada);
            return "/farmacista/ver_orden_venta";
        } else {

            return "/farmacista/errorPages/no_existe_orden";
        }
    }


    @PostMapping("/farmacista/ver_orden_venta_tabla")
    public String verOrdenesVentaTabla(@RequestParam(value = "idOrden") String idOrdenTabla){

        idVerOrdenCreada = Integer.valueOf(idOrdenTabla);

        return "redirect:/farmacista/ver_orden_venta";
    }

    @GetMapping("/farmacista/ordenes_venta")
    public String tablaOrdenesVenta(Model model) {
        List<Orden> listaOrdenesVenta = ordenRepository.findAll();
        model.addAttribute("listaOrdenesVenta", listaOrdenesVenta);
        return "/farmacista/ordenes_venta";
    }

    @GetMapping("/farmacista/ordenes_web")
    public String tablaOrdenesWeb(Model model) {
        List<Orden> listaOrdenesWeb = ordenRepository.findAllOrdenesWeb();
        model.addAttribute("listaOrdenesWeb", listaOrdenesWeb);
        return "/farmacista/ordenes_web";
    }
    @GetMapping("/verOrdenWeb")
    public String verOrdenWeb(Model model,
                              @RequestParam("id") Integer id) {
        Optional<Orden> optionalOrden = ordenRepository.findById(id);
        if(optionalOrden.isPresent()){
            Orden ordenWeb = optionalOrden.get();
            model.addAttribute("ordenWeb", ordenWeb);
            return "farmacista/ver_orden_web";
        }else {
            return  "redirect:/farmacista/ordenes_web";
        }
    }

    @GetMapping("/farmacista/pre_ordenes")
    public String tablaPreOrdenes(Model model) {
        List<Orden> listaPreOrdenes = ordenRepository.findAllPreOrdenes();
        model.addAttribute("listaPreOrdenes", listaPreOrdenes);
        return "/farmacista/pre_ordenes";
    }

    @GetMapping("/verPreOrden")
    public String verPreOrden(Model model,
                              @RequestParam("id") Integer id) {
        Optional<Orden> optionalOrden = ordenRepository.findById(id);
        if(optionalOrden.isPresent()){
            Orden preorden = optionalOrden.get();
            model.addAttribute("preorden", preorden);
            return "farmacista/ver_pre_orden";
        }else {
            return  "redirect:/farmacista/pre_ordenes";
        }
    }

    @GetMapping("/farmacista/perfil")
    public String profile() {
        return "/farmacista/perfil";
    }
    @GetMapping("/farmacista/facturacion")
    public String facturacion() {
        return "/farmacista/facturacion";
    }
    @GetMapping("/farmacista/cambioContraseña")
    public String cambioContra() {
        return "/farmacista/cambioContraseña";
    }


    public List<Medicamento> getMedicamentosFromLista(List<String> listaSelectedIds) {
        List<Optional<Medicamento>> optionals = new ArrayList<>();
        List<Medicamento> seleccionados;
        for (int i = 0; i < listaSelectedIds.size(); i += 2) {
            optionals.add(medicamentoRepository.findById(Integer.valueOf(listaSelectedIds.get(i))));
        }
        seleccionados = optionals.stream().flatMap(Optional::stream).collect(Collectors.toList());

        return seleccionados;
    }

    public List<String> getCantidadesFromLista(List<String> listaSelectedIds) {
        List<String> cantidades = new ArrayList<>();
        for (int i = 0; i + 1 < listaSelectedIds.size(); i += 2) {
            cantidades.add(listaSelectedIds.get(i + 1));
        }
        return cantidades;
    }


    @Getter
    public class verificationStock{
        private final List<Medicamento> medicamentosSinStock;
        private final List<Medicamento> medicamentosConStock;
        private final List<String> cantidadesFaltantes;
        private final List<String> cantidadesExistentes;

        public verificationStock(List<Medicamento> medicamentosSeleccionados, List<String> listaCantidades) {
            List<Medicamento> medicamentosSinStock = new ArrayList<>();
            List<Medicamento> medicamentosConStock = new ArrayList<>();
            List<String> cantidadesFaltantes = new ArrayList<>();
            List<String> cantidadesExistentes = new ArrayList<>();

            int i = 0;

            /* Usaremos la Sede 1 porque aún no contamos con Session*/
            SedeStockId sedeStockId = new SedeStockId();
            sedeStockId.setIdSede(1);

            for (Medicamento med : medicamentosSeleccionados) {

                sedeStockId.setIdMedicamento(med.getIdMedicamento());
                Optional<SedeStock> sedeStockOptional = sedeStockRepository.findById(sedeStockId);

                if (sedeStockOptional.isPresent()) {
                    SedeStock sedeStock = sedeStockOptional.get();

                    Medicamento medicamentoToVerify = sedeStock.getIdMedicamento();

                    if(Integer.parseInt(listaCantidades.get(i)) > sedeStock.getCantidad()){
                        medicamentosSinStock.add(medicamentoToVerify);
                        cantidadesFaltantes.add(String.valueOf(Integer.parseInt(listaCantidades.get(i)) - sedeStock.getCantidad()));
                    } else {
                        medicamentosConStock.add(medicamentoToVerify);
                        cantidadesExistentes.add(listaCantidades.get(i));
                    }

                } else {
                    medicamentosSinStock.add(med);
                    cantidadesFaltantes.add(listaCantidades.get(i));
                }

                i++;
            }

            this.medicamentosSinStock = medicamentosSinStock;
            this.medicamentosConStock = medicamentosConStock;
            this.cantidadesFaltantes = cantidadesFaltantes;
            this.cantidadesExistentes = cantidadesExistentes;
        }
    }

}


