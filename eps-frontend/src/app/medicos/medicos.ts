import { ChangeDetectorRef, Component, computed, inject, PLATFORM_ID, signal } from '@angular/core';
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
  medicos = signal<Medico[]>([]);
  medico : any = new Medico;
  tipo : number = 1
  paginaActual = signal(1);
  itemsPorPagina = 4;

  private detector = inject(PLATFORM_ID)
  private cdr = inject(ChangeDetectorRef)

  constructor(private servicioMedico: MedicoService){}

  ngOnInit(): void{
    if(isPlatformBrowser(this.detector)){
      this.listarP()
    }
  }

  private listarP(){
    this.servicioMedico.obtenerMedicos().subscribe(dato => {
      this.medicos.set(dato)
      console.log(dato)
      this.cdr.detectChanges();
    })
  }

  datosPaginados = computed(() => {
    const inicio = (this.paginaActual() - 1) * this.itemsPorPagina;
    const fin = inicio + this.itemsPorPagina;
    return this.medicos().slice(inicio, fin);
  })

  totalPaginas = computed(() => 
  Math.ceil(this.medicos().length / this.itemsPorPagina));

  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 1 && nuevaPagina <= this.totalPaginas()) {
      this.paginaActual.set(nuevaPagina);
    }
  }

  abrirModal(){
    const modal = document.getElementById("registro")
    if(modal!=null)
      modal.style.display='block';
  }

  cerrarModal(){
    this.medico = new Medico;
    const modal = document.getElementById("registro")
    if(modal!=null)
      modal.style.display='none';
    this.tipo=1
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
        this.listarP()
    })
      }
    }
  }

  actualizarMedico(p: Medico){
    this.tipo = 0
    this.medico = p
    this.abrirModal()
  }

  eliminar(cc: String){
    this.servicioMedico.eliminarMedico(cc).subscribe(dato => {
      console.log(dato)
      this.listarP()
    })
  }

  buscar(){
    const cedula = (document.getElementById("cedula") as HTMLInputElement).value;
    this.servicioMedico.buscarMedico(cedula).subscribe(dato => {
      this.medico = dato
      this.tipo = 2
      this.abrirModal()
      this.cdr.detectChanges(); 
    })
  }
}
