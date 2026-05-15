package eps.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eps.modelo.Medico;

public interface medico extends JpaRepository<Medico, String>{
	public List<Medico> findByNombre(String nombre);
	public List<Medico> findByApellido(String apellido);
	public List<Medico> findByEspecialidad(String especialidad);
	public Medico findFirstByEspecialidad(String especialidad);
}
