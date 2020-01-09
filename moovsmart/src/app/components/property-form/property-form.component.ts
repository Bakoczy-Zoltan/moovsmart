import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Cloudinary } from '@cloudinary/angular-5.x';
import { PropertyFormDataModel } from '../../models/propertyFormData.model';
import { ImageService } from '../../services/image.service';
import { PropertyService } from '../../services/property.service';
import { validationHandler } from '../../utils/validationHandler';


@Component({
  selector: 'app-property-form',
  templateUrl: './property-form.component.html',
  styleUrls: ['./property-form.component.css']
})
export class PropertyFormComponent implements OnInit {

    counties: CountyOptionModel[];
    propertyTypes: PropertyTypeOptionModel[];
    propertyStates: PropertyStateOptionModel[];
    display = 'none';

    registratedUser: boolean;
    private propertyId: number;
    imgUrl: any;
    selectedFile: File;
    searchPosition: string;
    geocoder: google.maps.Geocoder;
    addressToDecode: google.maps.GeocoderRequest = {};
    actualUserName: string;
    lngCoord: number;
    latCoord: number;
    answer: string[];
    answerPublicId: string[];
    answerUrl: string[];


    propertyForm = this.formBuilder.group({
        'name': ['', Validators.compose([Validators.required, Validators.minLength(3),
            Validators.maxLength(60)])],
        'area': ['', Validators.compose([Validators.required, Validators.min(1)])],
        'numberOfRooms': ['', Validators.compose([Validators.min(1), Validators.max(12)])],
        'buildingYear': ['', Validators.min(0)],
        'propertyType': [''],
        'propertyState': [''],
        'county': [''],
        'city': ['', Validators.compose([Validators.required, Validators.minLength(2)])],
        'zipCode': ['', Validators.compose([Validators.required, Validators.min(1000), Validators.max(9999)])],
        'street': ['', Validators.compose([Validators.required, Validators.minLength(2)])],
        'streetNumber': ['', Validators.compose([Validators.required, Validators.minLength(1)])],
        'description': ['', Validators.minLength(10)],
        'price': ['', Validators.min(1)],
        'imageUrl': [[''],],
    });


    constructor(private formBuilder: FormBuilder,
                private propertyService: PropertyService,
                private route: ActivatedRoute,
                private router: Router,
                private imageService: ImageService,
                private cloudinary: Cloudinary) {

        this.geocoder = new google.maps.Geocoder();
        // this.searchPosition = '1035 Szentendrei ut Budapest 14';
        // this.addressToDecode.address = this.searchPosition;


    }

    ngOnInit() {
        this.propertyService.getInitialFormData().subscribe((formInitData: FormInitDataModel) => {
            this.counties = formInitData.counties;
            this.propertyTypes = formInitData.propertyTypes;
            this.propertyStates = formInitData.propertyStates;
        });

        this.actualUserName = this.propertyService.userName2;
        this.propertyService.userName.subscribe(
            (name) => {
                this.actualUserName = name;
                console.log('NAME' + name);
                this.registratedUser = name !== null;
                if (this.actualUserName == null) {
                    this.openModalDialog();
                } else {
                    this.closeDial();
                }
            });

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
        this.codeAddress();
    }

    getPropertyData = (id: string) => {
        this.propertyService.fetchPropertyData(id).subscribe(
            (response: PropertyFormDataModel) => {
                this.propertyForm.patchValue({
                    name: response.name,
                    area: response.area,
                    numberOfRooms: response.numberOfRooms,
                    buildingYear: response.buildingYear,
                    propertyType: response.propertyType,
                    propertyState: response.propertyState,
                    county: response.county,
                    city: response.city,
                    zipCode: response.zipCode,
                    street: response.street,
                    streetNumber: response.streetNumber,
                    description: response.description,
                    price: response.price,
                    imageUrl: response.imageUrl,
                });
            },
        );
    };

    submit = () => {
        const formData = {...this.propertyForm.value};

        this.searchPosition = formData.zipCode + ' ' + formData.street + ' ' + formData.city + ' ' + formData.streetNumber;
        this.addressToDecode.address = this.searchPosition;
        this.codeAddress();

        formData.lngCoord = this.lngCoord;
        formData.latCoord = this.latCoord;

        formData.isValid = true;
        formData.owner = this.actualUserName;

        if (this.selectedFile != null) {
            this.imageService.uploadImage(this.selectedFile).subscribe(
                (data) => {
                    this.answer = data;

                    this.answerPublicId = [this.answer[0]];
                    this.answerUrl = [this.answer[1]];

                    formData.publicId = this.answerPublicId;
                    formData.imageUrl = this.answerUrl;

                    console.log(formData.publicId);
                    debugger;

                    this.selectedFile = null;
                },
                () => {},
                () => {
                    console.log('COMPLETE', formData);
                    this.propertyId ? this.updateProperty(formData) : this.createNewProperty(formData);
                },
            );
        } else {
            this.propertyId ? this.updateProperty(formData) : this.createNewProperty(formData);
        }
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

    processFile(event) {

        this.selectedFile = event.target.files[0];

        let reader = new FileReader();
        reader.readAsDataURL(event.target.files[0]);
        reader.onload = (event2) => {
            this.imgUrl = reader.result;
        };
    }

    codeAddress() {
        this.geocoder.geocode(this.addressToDecode, (results: google.maps.GeocoderResult[], status: google.maps.GeocoderStatus) => {
            if (status === google.maps.GeocoderStatus.OK) {
                // this.locationCoordinates[0] = results[0].geometry.location.lat();
                // this.locationCoordinates[1] = results[0].geometry.location.lng();
                this.lngCoord = results[0].geometry.location.lat();
                this.latCoord = results[0].geometry.location.lng();

            } else {
                console.log(
                    'Geocoding service: geocode was not successful for the following reason: '
                    + status,
                );
                console.log(status + ' error');
            }
        });
    }

    openModalDialog() {
        this.display = 'block';
    }

    closeDial() {
        this.display = 'none';
        this.router.navigate(['signin']);
    }

    backToList() {
        this.display = 'none';
        this.router.navigate(['property-list']);
    }
}
