package eps.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eps.repositorio.citas;
import eps.repositorio.paciente;
import eps.modelo.Citas;
import eps.modelo.Paciente;

@RestController
@RequestMapping("/pacientes/p/")
@CrossOrigin(origins= "http://localhost:4200")
public class ControladoraPaciente {
	
	@Autowired
	private paciente repositorioPaciente;
	
	@Autowired
	private citas repositorioCitas;
	
	@GetMapping("listarTodo/")
	public List<Paciente> mostrarTodos(){
		return repositorioPaciente.findAll();
	}
	
	@PostMapping("buscarCC/")
	public Paciente buscarCC(@RequestBody String cedula) {
		Paciente p = repositorioPaciente.findById(cedula).get();
		return p;
	}
	
	@PostMapping("guardar/")
	public ResponseEntity<Paciente> adicionar(@RequestBody Paciente p) {
		repositorioPaciente.save(p);
		return ResponseEntity.ok(p);
	}
	
	@PostMapping("eliminar/")
	public Optional<Paciente> eliminar(@RequestBody String cedula) {
		Paciente p = this.repositorioPaciente.findById(cedula).get();
		List<Citas> c = this.repositorioCitas.findByPaciente(p);
		
		for(int i = 0; i < c.size(); i++) {
			this.repositorioCitas.deleteById(c.get(i).getNumeroCita());
		}
		this.repositorioPaciente.deleteById(cedula);
		return Optional.empty();
	}
	
	@GetMapping("listarPorNombres/")
	public List<Paciente> listarPorNombre(@RequestParam ("nombre") String n){
		List<Paciente> p = repositorioPaciente.findByNombre(n);
		return p;
	}
	
	@GetMapping("listarPorApellidos/")
	public List<Paciente> listarPorApellidos(@RequestParam ("apellido") String a){
		List<Paciente> p = repositorioPaciente.findByApellido(a);
		return p;
	}
	
	@GetMapping("listarPorEdad/")
	public List<Paciente> listarPorEdad(@RequestParam ("edad") int e){
		
		List<Paciente> p = repositorioPaciente.findByEdad(e);
		return p;
	}
	
	@GetMapping("mayoresEdad/")
	public List<Paciente> mayoresEdad(){
		List<Paciente> p = repositorioPaciente.mayoresEdad();
		return p;
	}
}
