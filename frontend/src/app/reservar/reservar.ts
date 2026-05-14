import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Viaje } from '../entidades/viaje';
import { ViajeService } from '../servicio/viaje';

@Component({
  selector: 'app-reservar',
  imports: [FormsModule],
  templateUrl: './reservar.html',
  styleUrl: './reservar.css',
})
export class Reservar {
  destino: String = "";
  fecha: String = "";
  buscado: boolean = false;
  viajesEncontrados: Viaje[] = [];

  constructor(private viajeService: ViajeService) {}

  buscar() {
    if (!this.destino || !this.fecha) {
      alert("Introduce destino y fecha, por favor");
      return;
    }
    
    this.viajeService.consultarViaje(this.destino, this.fecha).subscribe(dato => {
      this.viajesEncontrados = dato;
      this.buscado = true;
    });
  }
}