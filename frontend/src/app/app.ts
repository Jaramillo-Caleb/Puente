import { Component, signal } from '@angular/core';
import { Navegacion } from './navegacion/navegacion';
import { Footer } from './footer/footer';

@Component({
  selector: 'app-root',
  imports: [Navegacion, Footer],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
}
