package eps.modelo;

import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="citas")
public class Citas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numeroCita;
	
	@Column(name="fecha")
	private Date fecha;
	
	@Column(name="hora")
	private LocalTime hora;
	
	@Column(name="estado")
	private String estado;
	
	@ManyToOne ()
	@JoinColumn(name="id_paciente", referencedColumnName="cc")
	private Paciente paciente;
	
	@ManyToOne ()
	@JoinColumn(name="id_medico", referencedColumnName="cc")
	private Medico id_medico;

	public Citas(Date fecha, LocalTime hora, String estado, Paciente paciente, Medico medico) {
		this.fecha = fecha;
		this.hora = hora;
		this.estado = estado;
		this.paciente = paciente;
		this.id_medico = medico;
	}

	public Citas() {}

	public Long getNumeroCita() {
		return numeroCita;
	}

	public void setNumeroCita(Long numeroCita) {
		this.numeroCita = numeroCita;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Paciente getId_paciente() {
		return paciente;
	}

	public void setId_paciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getId_medico() {
		return id_medico;
	}

	public void setiId_medico(Medico medico) {
		this.id_medico = medico;
	}
}
