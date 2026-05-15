import { ChangeDetectorRef, Component, inject, PLATFORM_ID } from '@angular/core';
import { Medico } from '../entidades/medico';
import { MedicoService } from '../servicio/medico';
import { isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-medicos',
  imports: [FormsModule],
  templateUrl: './medicos.html',
  styleUrl: './medicos.css',
})
export class Medicos {
  medicos : Medico[];
  medico : any = new Medico;

  private detector = inject(PLATFORM_ID)
  private cdr = inject(ChangeDetectorRef)

  constructor(private servicioMedico: MedicoService){}

  ngOnInit(): void{
    if(isPlatformBrowser(this.detector)){
      this.listarM()
    }
  }

  private listarM(){
    this.servicioMedico.obtenerMedicos().subscribe(dato => {
      this.medicos = dato;
      console.log(dato)
      this.cdr.detectChanges();
    })
  }

  abrirModal(){
    const modal = document.getElementById("registro")
    if(modal!=null)
      modal.style.display='block';
  }

  cerrarModal(){
    const modal = document.getElementById("registro")
    if(modal!=null)
      modal.style.display='none';
  }

  guardarMedico(){
    for(let p in this.medico){
      if(this.medico[p] == null){
        alert("ups, faltan datos")
        return 
      }else{
        this.servicioMedico.guardarMedico(this.medico).subscribe(dato => {
        console.log(dato)
        this.cerrarModal()
        this.listarM()
    })
      }
    }
  }
}
