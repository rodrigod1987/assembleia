import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-pauta',
  templateUrl: './pauta.component.html',
  styleUrls: ['./pauta.component.css']
})
export class PautaComponent implements OnInit {

  pautas: any;

  constructor(@Inject('BASE_API_URL') private apiUrl: string,
    private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.httpClient.get(this.apiUrl+"/v1/pauta")
      .subscribe(response => { this.pautas = response})
  }

}
