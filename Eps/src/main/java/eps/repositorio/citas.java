package eps.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import eps.modelo.Paciente;
import eps.modelo.Citas;

public interface citas extends JpaRepository<Citas, Long>{
	
	List <Citas> findByPaciente(Paciente p);
	
	@Query(value="SELECT DISTINCT CONCAT(m.nombre, \" \", m.apellido) AS nombre_medicos" 
			+ " FROM citas c"
			+ " INNER JOIN medicos m"
			+ " ON c.id_medico = m.cc"
			+ " WHERE c.estado = \"asignada\"", nativeQuery=true)
	public List<String> listarMedicosAsignados();
	
	@Query(value="SELECT m.especialidad, m.nombre, c.hora, c.fecha "
			+ " FROM citas c"
			+ " INNER JOIN medicos m"
			+ " ON c.id_medico = m.cc"
			+ " WHERE c.id_paciente = :id", nativeQuery=true)
	public List<Object> datosCitaPaciente(@Param ("id") String id);
	
	@Query(value="SELECT COUNT(*)"
			+ " FROM citas"
			+ " WHERE fecha = :fecha_cita AND estado = \"asignada\"", nativeQuery=true)
	public int citasPorFecha(@Param ("fecha_cita") String fecha_cita);
	
	@Query(value="SELECT COUNT(*)"
			+ " FROM citas"
			+ " WHERE fecha = :fecha_cita AND estado = \"cancelada\"", nativeQuery=true)
	public int citasCanceladasPorFecha(@Param ("fecha_cita") String fecha_cita);
	
	@Modifying  
	@Transactional
	@Query(value="UPDATE citas"
			+ " SET estado = \"asistida\""
			+ " WHERE id_paciente = :id", nativeQuery=true)
	public void cambiarEstadoCita(@Param ("id") String id);
	
	@Query(value="SELECT m.nombre, COUNT(*)"
			+ " FROM citas c"
			+ " INNER JOIN medicos m"
			+ " ON c.id_medico = m.cc"
			+ " WHERE c.estado = \"asignada\" AND c.fecha = :fecha_cita"
			+ " GROUP BY m.cc", nativeQuery=true)
	public List<Object> citasMedicoPorFecha(@Param ("fecha_cita") String fecha_cita);
	
	@Query(value="SELECT COUNT(*)"
			+ " FROM citas"
			+ " WHERE estado != \"cancelada\" AND fecha = :fecha_cita", nativeQuery = true)
	public int cantidadPacientesPorFecha(@Param ("fecha_cita") String fecha_cita);
	
	@Query(value="SELECT AVG(p.edad)"
			+ " FROM citas c"
			+ " INNER JOIN pacientes p"
			+ " ON c.id_paciente = p.cc"
			+ " INNER JOIN medicos m"
			+ " ON c.id_medico = m.cc"
			+ " WHERE p.genero = \"F\" AND m.especialidad = \"Cardiólogo\"", nativeQuery=true)
	public Double promedioEdadMujeresCardiologia();
	
	@Query(value=" SELECT COUNT(*) * 20000, m.nombre"
			+ " FROM citas c"
			+ " INNER JOIN medicos m"
			+ " ON c.id_medico = m.cc"
			+ " WHERE c.estado = \"asignada\""
			+ " GROUP BY c.id_medico"
			+ " HAVING COUNT(*) > 2", nativeQuery=true)
	public List<Object> medicosBono();
}
