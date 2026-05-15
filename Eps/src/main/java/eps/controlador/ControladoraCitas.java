package eps.controlador;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eps.repositorio.citas;
import eps.repositorio.paciente;
import eps.repositorio.medico;
import eps.modelo.Citas;
import eps.modelo.Medico;
import eps.modelo.Paciente;

@RestController
@RequestMapping("/citas")
public class ControladoraCitas {
 
	@Autowired
	private citas repositorioCitas;
	@Autowired
	private paciente repositorioPacientes;
	@Autowired
	private medico repositorioMedico;
	
	@GetMapping("listarTodo/")
	public List<Citas> mostrarTodos(){
		return repositorioCitas.findAll();
	}
	
	@PostMapping("GuardarCita/")
	public ResponseEntity<?> guardar(@RequestBody Citas c) {
		String cedula_p = c.getId_paciente().getCedula();
		String cedula_m = c.getId_medico().getCedula();
		if(repositorioPacientes.existsById(cedula_p) && repositorioMedico.existsById(cedula_m)) {
			Paciente p = repositorioPacientes.findById(cedula_p).get();
			Medico m = repositorioMedico.findById(cedula_m).get();
			Citas cita = new Citas(c.getFecha(), c.getHora(), c.getEstado(), p, m);
			repositorioCitas.save(cita);
			return ResponseEntity.ok(cita);
		}else {
			return ResponseEntity.ok("Los datos son incorrectos");
		}
	}
	
	@GetMapping("buscarCita/")
	public ResponseEntity<?> buscar(@RequestParam ("cita") String cita){
		Long citaId = Long.parseLong(cita);
		Citas c = repositorioCitas.findById(citaId).get();
		if(repositorioCitas.existsById(citaId)) {
			return ResponseEntity.ok(c);
		}else {
			return ResponseEntity.ok("cita no encontrada");
		}
	}
	
	@DeleteMapping("borrarCita/")
	public ResponseEntity<?> borrar(@RequestParam ("cita") String cita){
		Long citaId = Long.parseLong(cita);
		Citas c = repositorioCitas.findById(citaId).get();
		if(repositorioCitas.existsById(citaId)) {
			repositorioCitas.deleteById(citaId);
			return ResponseEntity.ok("Eliminado");
		}else {
			return ResponseEntity.ok("La cita no existe");
		} 
	}
	
	@GetMapping("listarMedicosAsignados/")
	public List<String> listarMedicosAsignados(){
		List<String> c = repositorioCitas.listarMedicosAsignados();
		return c;
	}
	
	@GetMapping("datosCitaPaciente/")
	public List<Object> datosCitaPaciente(@RequestParam ("id") String id) {
		List<Object> datos = repositorioCitas.datosCitaPaciente(id);
		return datos;
	}
	
	@GetMapping("citasPorFecha/")
	public int citasPorFecha(@RequestParam ("fecha_cita") String fecha_cita) {
		int cantidad = repositorioCitas.citasPorFecha(fecha_cita);
		return cantidad;
	}
	
	@GetMapping("citasCanceladasPorFecha/")
	public int citasCanceladasPorFecha(@RequestParam ("fecha_cita") String fecha_cita) {
		int cantidad = repositorioCitas.citasCanceladasPorFecha(fecha_cita);
		return cantidad;
	}
	
	@PutMapping("cambiarEstadoCita/")
	public String cambiarEstadoCita(@RequestParam ("id") String id) {
		try {
			repositorioCitas.cambiarEstadoCita(id);
			return "Cambiado correctamente";
		} catch (Exception e) {
			return "ups, hubo un error" + e;
		}
	}
	
	@GetMapping("citasMedicoPorFecha/")
	public List<Object> citasMedicoPorFecha(@RequestParam ("fecha_cita") String fecha_cita){
		List<Object> lista = repositorioCitas.citasMedicoPorFecha(fecha_cita);
		return lista;
	}
 
	@GetMapping("cantidadPacientesPorFecha/")
	public int cantidadPacientesPorFecha(@RequestParam ("fecha_cita") String fecha_cita) {
		int cantidad = repositorioCitas.cantidadPacientesPorFecha(fecha_cita);
		return cantidad;
	}
	
	@PostMapping("asignarCitaPorEdadYGenero/")
	public ResponseEntity<String> asignarCitaPorEdadYGenero(){
		List<Paciente> pacientes = repositorioPacientes.buscarPacientePorGeneroYEdad();
		
		Medico ginecologo = repositorioMedico.findFirstByEspecialidad("Ginecología");
		Medico urologo = repositorioMedico.findFirstByEspecialidad("Urólogo");
		
		for (int i = 0; i < pacientes.size(); i++) {
		    Paciente p = pacientes.get(i);
		    Medico asignado;
		    if (p.getGenero() == 'F') {
		        asignado = ginecologo;
		    } else {
		        asignado = urologo;
		    }

		    Citas nueva = new Citas(new Date(), LocalTime.now(), "asignada", p, asignado);
		    repositorioCitas.save(nueva);
		}
		return ResponseEntity.ok("Se generaron las citas.");
	}
	
	
	@GetMapping("promedioEdadMujeresCardiologia/")
	public Double promedioEdadMujeresCardiologia() {
		Double promedio = repositorioCitas.promedioEdadMujeresCardiologia();
		return promedio; 
	}
	
	@GetMapping("medicosBono/")
	public List<Object> medicosBono(){
		List<Object> medicos = repositorioCitas.medicosBono();
		return medicos;
	}
}