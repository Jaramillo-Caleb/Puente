import { ChangeDetectorRef, Component, computed, inject, PLATFORM_ID, signal } from '@angular/core';
import { Paciente } from '../entidades/paciente';
import { PacienteService } from '../servicio/paciente';
import { isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-pacientes',
  imports: [FormsModule],
  templateUrl: './pacientes.html',
  styleUrl: './pacientes.css',
})
export class Pacientes {
  pacientes = signal<Paciente[]>([]);
  paciente : any = new Paciente;
  tipo : number = 1
  paginaActual = signal(1);
  itemsPorPagina = 4;

  private detector = inject(PLATFORM_ID)
  private cdr = inject(ChangeDetectorRef)

  constructor(private servicioPaciente: PacienteService){}

  ngOnInit(): void{
    if(isPlatformBrowser(this.detector)){
      this.listarP()
    }
  }

  private listarP(){
    this.servicioPaciente.obtenerPacientes().subscribe(dato => {
      this.pacientes.set(dato)
      console.log(dato)
      this.cdr.detectChanges();
    })
  }

  datosPaginados = computed(() => {
    const inicio = (this.paginaActual() - 1) * this.itemsPorPagina;
    const fin = inicio + this.itemsPorPagina;
    return this.pacientes().slice(inicio, fin);
  })

  totalPaginas = computed(() => 
  Math.ceil(this.pacientes().length / this.itemsPorPagina));

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
    this.paciente = new Paciente;
    const modal = document.getElementById("registro")
    if(modal!=null)
      modal.style.display='none';
    this.tipo=1
  }

  guardarPaciente(){
    for(let p in this.paciente){
      if(this.paciente[p] == null){
        alert("ups, faltan datos")
        return 
      }else{
        this.servicioPaciente.guardarPaciente(this.paciente).subscribe(dato => {
        console.log(dato)
        this.cerrarModal()
        this.listarP()
    })
      }
    }
  }

  actualizarPaciente(p: Paciente){
    this.tipo = 0
    this.paciente = p
    this.abrirModal()
  }

  eliminar(cc: String){
    this.servicioPaciente.eliminarPaciente(cc).subscribe(dato => {
      console.log(dato)
      this.listarP()
    })
  }

  buscar(){
    
    const cedula = (document.getElementById("cedula") as HTMLInputElement).value;
    this.servicioPaciente.buscarPaciente(cedula).subscribe(dato => {
      this.paciente = dato
      this.tipo = 2
      this.abrirModal()
      this.cdr.detectChanges(); 
    })
  }
}
