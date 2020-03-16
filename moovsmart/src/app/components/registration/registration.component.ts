import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {PropertyFormDataModel} from '../../models/propertyFormData.model';
import {UserFormDataModel} from '../../models/userFormData.model';
import {PropertyService} from '../../services/property.service';
import {validationHandler} from '../../utils/validationHandler';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  display = 'none';
  displayError = 'none';

  registrationForm = this.formBuilder.group({
    "userName": ['', Validators.compose([Validators.required,
      Validators.minLength(2), Validators.maxLength(30)])],
    "mail": ['', Validators.compose([Validators.required,
      Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")])],
    "password": ['', Validators.compose([Validators.required,
      Validators.pattern("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}")])],
    "check": ['', Validators.required]
  });

  constructor(private formBuilder: FormBuilder,
              private propertyService: PropertyService,
              private router: Router) {

    console.log("regsitration comp")
  }

  ngOnInit() {
  }

  onSubmit = () => {
    const data = {...this.registrationForm.value};
    this.createNewUser(data);

  };

  createNewUser(data: UserFormDataModel) {
    this.openModalDialog();
    this.propertyService.registerUser(data).subscribe(
      (mess) => console.log(mess),
      // error => validationHandler(error, this.registrationForm),
      error => {
        this.closeModalDialog();
        this.openModalDialogError()
      },
    );
  }

  openModalDialog() {
    this.display = 'block';
  }

  closeModalDialog() {
    this.display = 'none';
  }

  openModalDialogError() {
    this.displayError = 'block';
  }

  closeModalDialogError() {
    this.displayError = 'none';
  }

  routeToPropertyList() {
    this.router.navigate(['property-list']);
  }
}
