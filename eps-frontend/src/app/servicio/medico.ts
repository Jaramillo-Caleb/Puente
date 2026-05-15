import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Medico } from '../entidades/medico'

@Injectable({
  providedIn: 'root',
})
export class MedicoService {
  constructor(private httpCliente: HttpClient){}
  private listaM = 'http://localhost:8080/medico/listarTodo/'
  private guardarM = 'http://localhost:8080/medico/guardar/'
  obtenerMedicos() : Observable<any>{
    return this.httpCliente.get(this.listaM)
  }

  guardarMedico(medico: Medico) : Observable<any>{
    return this.httpCliente.post(`${this.guardarM}`, medico)
  }
}
