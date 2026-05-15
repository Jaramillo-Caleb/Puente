package eps.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eps.modelo.Paciente;

public interface paciente extends JpaRepository<Paciente, String>{
	public List<Paciente> findByNombre(String nombre);
	public List<Paciente> findByApellido(String apellido);
	public List<Paciente> findByEdad(int edad);
	
	@Query(value="SELECT * FROM pacientes WHERE edad > 17", nativeQuery=true)
	public List<Paciente> mayoresEdad();
	
	@Query(value="SELECT *"
			+ " FROM pacientes"
			+ " WHERE (genero = 'F' AND edad > 40) OR (genero = 'M' AND edad > 50)", nativeQuery=true)
	public List<Paciente> buscarPacientePorGeneroYEdad();
}