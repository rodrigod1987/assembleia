import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonModule, HashLocationStrategy, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { PautaComponent } from './pauta/pauta.component';
import { PautaNovaComponent } from './pauta-nova/pauta-nova.component';
import { AssociadoComponent } from './associado/associado.component';
import { PautaEditComponent } from './pauta-edit/pauta-edit.component';
import { VotoComponent } from './voto/voto.component';
import { ResumoComponent } from './resumo/resumo.component';
import { environment } from 'src/environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    PautaComponent,
    PautaNovaComponent,
    AssociadoComponent,
    PautaEditComponent,
    VotoComponent,
    ResumoComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    { provide: 'BASE_API_URL', useValue: environment.apiUrl },
    { provide: 'BASE_SOCKET_URL', useValue: environment.socketUrl },
    { provide: LocationStrategy, useClass: HashLocationStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
