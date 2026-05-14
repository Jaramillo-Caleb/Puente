import { Injectable } from '@angular/core';
import { Automovil } from '../entidades/automovil';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AutomovilService {
  constructor(private httpCliente: HttpClient){}
}
