import { Injectable } from '@angular/core';
import { Viaje } from '../entidades/viaje';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ViajeService {
  constructor(private httpCliente: HttpClient){}

  private listarV = 'http://localhost:8080/viaje/listarViajes/';

  consultarViaje(destino: String, fecha: String) : Observable<Viaje[]>  {
    return this.httpCliente.get<Viaje[]>(`${this.listarV}${destino}/${fecha}`);
  }
}
