import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ValidUserModel } from '../../models/validUserModel';
import { PropertyService } from '../../services/property.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  signInForm = this.formBuilder.group({
    "userName": ['', Validators.compose([Validators.required,
      Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")])],
    "password": ['', Validators.compose([Validators.required,
      Validators.pattern("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}")])]
  });

  constructor(private formBuilder: FormBuilder,
              private serviceLogin: PropertyService,
              private route: ActivatedRoute,
              private propertyService: PropertyService,
              private router: Router) { }

  ngOnInit() {
    this.route.paramMap.subscribe(paramMap => {
          const idParam = paramMap.get('id');
          console.log("ID user: " + paramMap.get('id'));
          if (idParam) {
            this.validateUser(idParam);
          }
        },
    );
  }

  onSubmit = () => {
    const data = {...this.signInForm.value};
    this.serviceLogin.signIn(data).subscribe(
        (validUSer: ValidUserModel) => {
          localStorage.setItem('user', JSON.stringify(validUSer.role));
          console.log(validUSer.name + " NAME");
          //this.propertyService.userName2 = (validUSer.name);
          this.propertyService.regisTrated.next(true);
          this.router.navigate(['property-list']);
        },
        (err) => console.log(err),
    );

  };

  validateUser(id: string) {
    this.propertyService.validateUser(id).subscribe(
        (data: string) => {
          localStorage.setItem('user', data);
        },
        (err) => console.log(err),
    );
  }

  moveToRegistrate() {
    this.router.navigate(['registration']);
  }
}
