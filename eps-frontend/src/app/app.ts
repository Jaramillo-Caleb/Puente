import { Component, signal } from '@angular/core';
import { Navegacion } from './navegacion/navegacion';

@Component({
  selector: 'app-root',
  imports: [Navegacion],
  templateUrl: './app.html',
  styleUrl: './app.css'
})

export class App {
  protected readonly title = signal('How are you?');
}
