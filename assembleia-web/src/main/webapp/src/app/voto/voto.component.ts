import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-voto',
  templateUrl: './voto.component.html',
  styleUrls: ['./voto.component.css']
})
export class VotoComponent implements OnInit {

  interval;
  tempoRestante: number = 60;
  resposta : any = "Nao";
  pauta: any;
  cpf;

  constructor(@Inject("BASE_API_URL") private apiUrl: string,
    private activatedRoute: ActivatedRoute,
    private httpClient: HttpClient,
    private router: Router) { }

  ngOnInit(): void {

    let pautaId = this.activatedRoute.snapshot.paramMap.get('pautaId');
    this.cpf = this.activatedRoute.snapshot.paramMap.get('cpf');

    this.httpClient
      .get(this.apiUrl+"/v1/pauta/" + pautaId)
      .subscribe(pauta => { 
        this.pauta = pauta;
        this.tempoRestante = this.pauta.tempoExecucao; 
      });

    this.startTimer();
  }

  startTimer() {
    this.interval = setInterval(() => {
      if(this.tempoRestante > 0) {
        this.tempoRestante--;
      } else {
        this.navegar();
      }
    },1000);
  }

  votar() : void {
    let voto = { "pautaId": this.pauta.id, "cpf": this.cpf, "resposta": this.resposta };
    this.httpClient.post(`${this.apiUrl}/v1/voto`, voto)
    .subscribe(() => {
        this.navegar();
      });

  }

  navegar() : void {
    clearInterval(this.interval);
    this.router.navigate(['/resumo', this.pauta.id]);
  }

}
