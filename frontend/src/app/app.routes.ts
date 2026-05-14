import { Routes } from '@angular/router';
import { Reservar } from './reservar/reservar';

export const routes: Routes = [
    { path: '', redirectTo: 'reservar', pathMatch: 'full' },
    {path: 'reservar', component: Reservar}
];
