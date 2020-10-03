declare var SockJS;
declare var Stomp;

import { Component, OnInit, Input, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-pauta-edit',
  templateUrl: './pauta-edit.component.html',
  styleUrls: ['./pauta-edit.component.css']
})
export class PautaEditComponent implements OnInit {

  public stompClient;
  pauta: any;
  associados: any[];

  constructor(@Inject('BASE_API_URL') private apiUrl: string,
    @Inject('BASE_SOCKET_URL') private socketUrl: string,
    private httpClient: HttpClient,
    private activatedRoute: ActivatedRoute,
    private router: Router) {

      this.initializeWebSocketConnection();

  }

  ngOnInit(): void {

    const id =+ this.activatedRoute.snapshot.paramMap.get('id');
    
    this.httpClient.get(this.apiUrl+"/v1/pauta/"+id)
      .subscribe(response => this.pauta = response);
    
    this.httpClient.get(this.apiUrl+"/v1/associado/"+id)
      .subscribe((response: any[]) => this.associados = response);

  }

  initializeWebSocketConnection() {
    const ws = new SockJS(this.socketUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    
    this.stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      that.stompClient.subscribe('/topics/iniciar', (response) => {
        debugger;
        that.pauta = JSON.parse(response.body);
        let associado = JSON.parse(sessionStorage.getItem('associado'));
        that.router.navigate(['/voto', that.pauta.id, associado.cpf]);
      });
    });
  }

  iniciar() {
    debugger;
    this.stompClient.send('/app/iniciar', {}, JSON.stringify({id: this.pauta.id, seconds: 60 }));
  }

  ingressar() {
    debugger;
    let associado = JSON.parse(sessionStorage.getItem('associado'));
    
    if (associado) {
      const id =+ this.activatedRoute.snapshot.paramMap.get('id');

      this.httpClient.post(this.apiUrl+"/v1/pauta/"+id, associado)
      .subscribe(_ => { 
        this.associados.push(associado);
        return;
      });
    }
  }

}
