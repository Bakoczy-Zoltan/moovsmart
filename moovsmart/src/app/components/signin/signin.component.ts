import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PropertyService } from '../../services/property.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  signInForm = this.formBuilder.group({
    "userName": ['', Validators.compose([Validators.required,
      Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")])],
    "password": ['', Validators.compose([Validators.required,
      Validators.pattern("(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}")])]
  });

  constructor(private formBuilder: FormBuilder, private serviceLogin: PropertyService) { }

  ngOnInit() {
  }

  onSubmit = () => {
    const data = {...this.signInForm.value};
    console.log(data);
    this.serviceLogin.signIn(data).subscribe(
        (mess) => console.log(mess),
        (err) => console.log(err),
    );

  };

}
