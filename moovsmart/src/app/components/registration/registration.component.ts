import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PropertyFormDataModel } from '../../models/propertyFormData.model';
import { UserFormDataModel } from '../../models/userFormData.model';
import { PropertyService } from '../../services/property.service';
import { validationHandler } from '../../utils/validationHandler';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registrationForm = this.formBuilder.group({
    "userName": ['', Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(20)])],
    "mail": ['', Validators.compose([Validators.required, Validators.minLength(1)])],
    "password": ['', Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(20)])]
  });

  constructor(private formBuilder: FormBuilder,
              private propertyService: PropertyService,
              private router: Router) { }

  ngOnInit() {
  }

  onSubmit = () => {
    const data = {...this.registrationForm.value};
    this.createNewUser(data);

  };

  createNewUser(data: UserFormDataModel) {
    this.propertyService.registerUser(data).subscribe(
        () => this.router.navigate(['/']),
        error => validationHandler(error, this.registrationForm),
    );
  }

}
