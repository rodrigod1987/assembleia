import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, Form, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pauta-nova',
  templateUrl: './pauta-nova.component.html',
  styleUrls: ['./pauta-nova.component.css']
})
export class PautaNovaComponent implements OnInit {

  public form : FormGroup; 

  constructor(@Inject('BASE_API_URL') private apiUrl: string,
    private httpClient: HttpClient,
    private fb: FormBuilder,
    private router: Router) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      descricao: ['', Validators.required]
    });
  }

  nova() : void {
    let pauta = this.form.getRawValue();
    this.httpClient.post(this.apiUrl+"/v1/pauta", pauta)
      .subscribe((pauta: any) => { this.router.navigate(['/pauta', pauta.id]) });

  }

}
