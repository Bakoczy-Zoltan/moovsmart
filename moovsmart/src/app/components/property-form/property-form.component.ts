import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyFormDataModel } from '../../models/propertyFormData.model';
import { ImageService } from '../../services/image.service';
import { PropertyService } from '../../services/property.service';
import { validationHandler } from '../../utils/validationHandler';
import { Cloudinary } from '@cloudinary/angular-5.x';


@Component({
  selector: 'app-property-form',
  templateUrl: './property-form.component.html',
  styleUrls: ['./property-form.component.css']
})
export class PropertyFormComponent implements OnInit {

  private propertyId: number;
  selectedFile: File;


  propertyForm = this.formBuilder.group({
    "name": ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(60)])],
    "numberOfRooms": [0, Validators.compose([Validators.min(1), Validators.max(12)])],
    "price": [0, Validators.min(1)],
    "description": ['', Validators.minLength(10)],
    "imageUrl": ['']
  });


  constructor(private formBuilder: FormBuilder,
              private propertyService: PropertyService,
              private route: ActivatedRoute,
              private router: Router,
              private imageService: ImageService,
              private cloudinary: Cloudinary) {
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
            imageUrl: response.imageUrl.join(';')
          });
        },
    );
  };

  submit = () => {
    this.imageService.uploadImage(this.selectedFile).subscribe(
        (data) => {
          const formData = {...this.propertyForm.value};
          formData.isValid = true;
          formData.imageUrl = data;
          this.propertyId ? this.updateProperty(formData) : this.createNewProperty(formData);
        },
        () => {}
    ) ;

  };

  createNewProperty(data: PropertyFormDataModel) {
    this.propertyService.createProperty(data).subscribe(
        () => this.router.navigate(['property-list']),
        error => validationHandler(error, this.propertyForm),
    );
  }

  private updateProperty(data: PropertyFormDataModel) {
    this.propertyService.updateProperty(data, this.propertyId).subscribe(
        () => this.router.navigate(['']),
        error => validationHandler(error, this.propertyForm),
    );
  }

  processFile(imageInput: any) {
    this.selectedFile = imageInput.target.files[0];
  }
}
