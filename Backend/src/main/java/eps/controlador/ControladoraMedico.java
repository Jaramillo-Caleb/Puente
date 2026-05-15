package eps.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eps.repositorio.citas;
import eps.repositorio.medico;
import eps.modelo.Citas;
import eps.modelo.Medico;

@RestController
@RequestMapping("/medico")
@CrossOrigin(origins = "http://localhost:4200")
public class ControladoraMedico {
	
	@Autowired
	private medico repositorioMedico;
	
	@Autowired
	private citas repositorioCitas;
	
	@GetMapping("listarTodo/")
	public List<Medico> mostrarTodos(){
		return repositorioMedico.findAll();
	}
	
	@PostMapping("buscarCC/")
	public Medico buscarCC(@RequestBody String cedula) {
		Medico m = repositorioMedico.findById(cedula).get();
		return m;
	}
	
	@PostMapping("guardar/")
	public ResponseEntity<Medico> adicionar(@RequestBody Medico m) {
		repositorioMedico.save(m);
		return ResponseEntity.ok(m);
	}
	
	@PostMapping("eliminar/")
	public Optional<Medico> eliminar(@RequestBody String cedula) {
		Medico m = this.repositorioMedico.findById(cedula).get();
		List<Citas> c = this.repositorioCitas.findByMedico(m);
		
		for(int i = 0; i < c.size(); i++) {
			this.repositorioCitas.deleteById(c.get(i).getNumeroCita());
		}
		this.repositorioMedico.deleteById(cedula);
		return Optional.empty();
	}
	
	@PutMapping("actualizar/")
	public String actualizar(@RequestBody Medico m) {
		if(repositorioMedico.existsById(m.getCedula())) {
			Medico medico = repositorioMedico.findById(m.getCedula()).get();
			medico.setNombre(m.getNombre());
			medico.setApellido(m.getApellido());
			medico.setEspecialidad(m.getEspecialidad());
			return "Medico actualizado: " + "\n" + "nombre: " + m.getNombre() + "\n" + "apellido: " + m.getApellido() + "\n" + "especialidad: " + m.getEspecialidad() + "\n"  +  "cedula: " + m.getCedula();
		} else {
			repositorioMedico.save(m);
			return "Nuevo medico creado: " + "\n" + "nombre: " + m.getNombre() + "\n" + "apellido: " + m.getApellido() + "\n" + "especialidad: " + m.getEspecialidad() + "\n"  +  "cedula: " + m.getCedula();
		}		
	}
	
	@GetMapping("listarPorNombres/")
	public List<Medico> listarPorNombre(@RequestParam ("nombre") String n){
		List<Medico> m = repositorioMedico.findByNombre(n);
		return m;
	}
	
	@GetMapping("listarPorApellidos/")
	public List<Medico> listarPorApellidos(@RequestParam ("apellido") String a){
		List<Medico> m = repositorioMedico.findByApellido(a);
		return m;
	}
	
	@GetMapping("listarPorEspecialidad/")
	public List<Medico> listarPorEspecialidad(@RequestParam ("especialidad") String e){
		List<Medico> m = repositorioMedico.findByEspecialidad(e);
		return m;
	}
}
