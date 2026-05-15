import { Routes } from '@angular/router';
import { Pacientes} from './pacientes/pacientes';
import { Medicos} from './medicos/medicos';

export const routes: Routes = [
    {path: 'pacientes', component: Pacientes},
    {path: 'medicos', component: Medicos}
];
