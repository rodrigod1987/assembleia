declare var SockJS;
declare var Stomp;

import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-resumo',
  templateUrl: './resumo.component.html',
  styleUrls: ['./resumo.component.css']
})
export class ResumoComponent implements OnInit {

  public stompClient;
  pauta: any;
  resumoVotacao: any;

  constructor(@Inject('BASE_API_URL') private apiUrl: string,
    @Inject('BASE_SOCKET_URL') private socketUrl: string,
    private activatedRoute: ActivatedRoute,
    private httpClient: HttpClient) { 
    this.initializeWebSocketConnection();
  }

  ngOnInit(): void {
    let pautaId = this.activatedRoute.snapshot.paramMap.get("id");
    this.httpClient
      .get(this.apiUrl+"/v1/pauta/"+pautaId)
      .subscribe(pauta => this.pauta = pauta);

    this.httpClient
      .get(this.apiUrl+"/v1/pauta/resumo/"+pautaId)
      .subscribe(resumoVotacao => this.resumoVotacao = resumoVotacao);
  }

  initializeWebSocketConnection() {
    const ws = new SockJS(this.socketUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    
    this.stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      that.stompClient.subscribe('/topics/resumo', (response) => {
        that.resumoVotacao = JSON.parse(response.body);
      });
    });
  }

}
