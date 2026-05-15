package eps.modelo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name="medicos")
public class Medico {

	@Id
	@Column(name="cc")
	private String cedula;
	
	@Column(name="nombre", length=50, nullable=false)
	private String nombre;
	
	@Column(name="apellido", length=50, nullable=false)
	private String apellido;
	
	@Column(name="especialidad", length=50, nullable=false)
	private String especialidad;

	public Medico(String cedula, String nombre, String apellido, String especialidad) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.especialidad = especialidad;
	}

	public Medico() {}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
}