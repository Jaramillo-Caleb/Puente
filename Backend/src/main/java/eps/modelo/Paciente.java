package eps.modelo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name="pacientes")
public class Paciente {
	
	@Id
	@Column(name="cc")
	private String cedula;
	
	@Column(name="nombre", length=50, nullable=false)
	private String nombre;
	
	@Column(name="apellido", length=50, nullable=false)
	private String apellido;
	
	@Column(name="edad", nullable=false)
	private int edad;
	
	@Column(name="genero", nullable=false)
	private char genero;
	
	public Paciente(String cedula, String nombre, String apellido, int edad, char genero) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.genero = genero;
	}

	public Paciente() {}

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

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}
}
