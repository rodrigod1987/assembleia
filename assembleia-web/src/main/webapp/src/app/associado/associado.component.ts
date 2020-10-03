import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-associado',
  templateUrl: './associado.component.html',
  styleUrls: ['./associado.component.css']
})
export class AssociadoComponent implements OnInit {

  form: FormGroup;

  constructor(@Inject('BASE_API_URL') private apiUrl: string,
    private httpClient: HttpClient,
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {

    debugger;
    let associado = JSON.parse(sessionStorage.getItem('associado'));
    
    if (associado) {
      const id =+ this.activatedRoute.snapshot.paramMap.get('id');
      this.router.navigate(['/pauta', id]);
      return;
    }

    this.form = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', Validators.required]
    });

  }

  entrar() : void {
    debugger;
    let associado = this.form.getRawValue();
    
    this.httpClient.post(this.apiUrl+"/v1/associado", associado)
      .subscribe((associado) => { 
        sessionStorage.setItem('associado',JSON.stringify(associado))
        this.router.navigate(['/pauta'])
      });

  }

}
