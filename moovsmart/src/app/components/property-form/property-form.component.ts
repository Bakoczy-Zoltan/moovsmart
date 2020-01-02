import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PropertyFormDataModel } from '../../models/propertyFormData.model';
import { PropertyService } from '../../services/property.service';
import { validationHandler } from '../../utils/validationHandler';

@Component({
    selector: 'app-property-form',
    templateUrl: './property-form.component.html',
    styleUrls: ['./property-form.component.css'],
})
export class PropertyFormComponent implements OnInit {

    propertyForm = this.formBuilder.group({
        'name': ['', Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(60)])],
        'numberOfRooms': [0, Validators.min(1)],
        'price': [0, Validators.min(1)],
        'description': [''],
        'imageUrl': [''],
    });

    constructor(private formBuilder: FormBuilder,
                private propertyService: PropertyService,
                private router: Router) {
    }

    ngOnInit() {
    }

    submit = () => {
        const data = this.propertyForm.value;
        const img: string = this.propertyForm.value.imageUrl;
        data.imageUrl = [img];
        this.createNewProperty(data);
    };

    createNewProperty(data: PropertyFormDataModel) {
        this.propertyService.createProperty(data).subscribe(
            () => this.router.navigate(['property-list']),
            error => validationHandler(error, this.propertyForm),
        );
    }


}
