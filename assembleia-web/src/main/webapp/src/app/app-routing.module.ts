import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PautaComponent } from './pauta/pauta.component';
import { PautaNovaComponent } from './pauta-nova/pauta-nova.component';
import { AssociadoComponent } from './associado/associado.component';
import { PautaEditComponent } from './pauta-edit/pauta-edit.component';
import { VotoComponent } from './voto/voto.component';
import { ResumoComponent } from './resumo/resumo.component';


const routes: Routes = [
  { path: '', redirectTo: 'pauta', pathMatch: 'full' },
  {
    path: 'associado', component: AssociadoComponent,
    data: { title: 'Ingresso de Associado'}
  },
  {
    path: 'pauta',  component: PautaComponent,
    data: { title: 'Pauta' }
  },
  {
    path: 'pauta/nova', component: PautaNovaComponent,
    data: { title: 'Nova Pauta'}
  },
  {
    path: 'pauta/:id', component: PautaEditComponent,
    data: { title: 'Votacão da Pauta'}
  },
  {
    path: 'voto/:pautaId/:cpf', component: VotoComponent,
    data: { title: 'Votação'}
  },
  {
    path: 'resumo/:id', component: ResumoComponent,
    data: { title: 'Resumo Votação'}
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
