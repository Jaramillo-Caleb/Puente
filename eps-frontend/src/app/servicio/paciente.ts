import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Paciente } from '../entidades/paciente';

@Injectable({
  providedIn: 'root',
})
export class PacienteService {
  constructor(private httpCliente: HttpClient){}

  private listap = 'http://localhost:8080/pacientes/p/listarTodo/'
  private guardarP = 'http://localhost:8080/pacientes/p/guardar/'
  private eliminarP = 'http://localhost:8080/pacientes/p/eliminar/'
  private buscarP = 'http://localhost:8080/pacientes/p/buscarCC/'

  obtenerPacientes() : Observable<any>{
    return this.httpCliente.get(this.listap)
  }

  guardarPaciente(paciente: Paciente) : Observable<any>{
    return this.httpCliente.post(`${this.guardarP}`, paciente)
  }

  eliminarPaciente(cc: String) : Observable<any>{
    return this.httpCliente.post(`${this.eliminarP}`, cc)
  }

  buscarPaciente(cedula: String) : Observable<any>{
    return this.httpCliente.post(`${this.buscarP}`, cedula)
  }
}
  