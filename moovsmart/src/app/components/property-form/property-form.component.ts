import {Component, OnInit} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { PropertyFormDataModel } from '../../models/propertyFormData.model';
import { PropertyService } from '../../services/property.service';
import { validationHandler } from '../../utils/validationHandler';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-property-form',
  templateUrl: './property-form.component.html',
  styleUrls: ['./property-form.component.css']
})
export class PropertyFormComponent implements OnInit {

  private propertyId: number;

  propertyForm = this.formBuilder.group({
    "name": ['', Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(60)])],
    "numberOfRooms": [0, Validators.min(1)],
    "price": [0, Validators.min(1)],
    "description": [''],
    "imageUrl": ['']
  });

  constructor(private formBuilder: FormBuilder,
              private propertyService: PropertyService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(
        paramMap => {
          const editablePropertyId = paramMap.get('id');
          if (editablePropertyId) {
            this.propertyId = +editablePropertyId;
            this.getPropertyData(editablePropertyId);
          }
        },
        error => console.warn(error),
    );
  }


  getPropertyData = (id: string) => {
    this.propertyService.fetchPropertyData(id).subscribe(
        (response: PropertyFormDataModel) => {
          this.propertyForm.patchValue({
            name: response.name,
            numberOfRooms: response.numberOfRooms,
            price: response.price,
            description: response.description,
            imageUrl: response.imageUrl[0]
          });
        },
    );
  };

  submit = () => {
    const data = {...this.propertyForm.value};
    const img : string[] = [this.propertyForm.value.imageUrl];
    data.isValid = true;
    data.imageUrl = img;
    this.propertyId ? this.updateProperty(data) : this.createNewProperty(data);

   };

  createNewProperty(data: PropertyFormDataModel) {
    this.propertyService.createProperty(data).subscribe(
        () => this.router.navigate(['property-list']),
        error => validationHandler(error, this.propertyForm),
    );
  }

  private updateProperty(data: PropertyFormDataModel) {
    this.propertyService.updateProperty(data, this.propertyId).subscribe(
        () => this.router.navigate(['/']),
        error => validationHandler(error, this.propertyForm),
    );
  }



}
