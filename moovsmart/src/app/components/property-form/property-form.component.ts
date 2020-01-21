import { Component, NgZone, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyFormDataModel } from '../../models/propertyFormData.model';
import { ImageService } from '../../services/image.service';
import { PropertyService } from '../../services/property.service';
import { validationHandler } from '../../utils/validationHandler';


@Component({
    selector: 'app-property-form',
    templateUrl: './property-form.component.html',
    styleUrls: ['./property-form.component.css'],
})
export class PropertyFormComponent implements OnInit {

    counties: CountyOptionModel[];
    propertyTypes: PropertyTypeOptionModel[];
    propertyStates: PropertyStateOptionModel[];
    display = 'none';
    displayLoadingCircle = false;

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
    answerPublicId = [''];
    answerUrl = [''];
    actualUrlList = [];
    actualPublicIdList = [];
    formData: any;
    editing: boolean = false;
    currentYear = new Date().getFullYear();
    storage: any;

    propertyForm = this.formBuilder.group({
        'name': ['', Validators.compose([Validators.required, Validators.minLength(3),
            Validators.maxLength(20)])],
        'area': ['', Validators.compose([Validators.required, Validators.min(1), Validators.pattern('^[0-9]+$')])],
        'numberOfRooms': ['', Validators.compose([Validators.min(1), Validators.max(12), Validators.pattern('^[0-9]+$')])],
        'buildingYear': ['', Validators.compose([Validators.min(0), Validators.pattern('^[0-9]+$'),
            Validators.max(new Date().getFullYear())])],

        'propertyType': ['', Validators.required],
        'propertyState': ['', Validators.required],
        'county': ['', Validators.required],
        'city': ['', Validators.compose([Validators.required, Validators.minLength(2)])],
        'zipCode': ['', Validators.compose([Validators.required, Validators.min(1000),
            Validators.max(9999), Validators.pattern('^[0-9]+$')])],

        'street': ['', Validators.compose([Validators.required, Validators.minLength(2)])],
        'streetNumber': ['', Validators.compose([Validators.required, Validators.minLength(1)])],
        'description': ['', Validators.minLength(10)],
        'price': [null, [Validators.required, Validators.min(1), Validators.pattern('^[0-9]+$')]],
        'imageUrl': [['']],
    });


    constructor(private formBuilder: FormBuilder,
                private propertyService: PropertyService,
                private route: ActivatedRoute,
                private router: Router,
                private imageService: ImageService,
                private ngZone: NgZone) {

        this.geocoder = new google.maps.Geocoder();

    }

    ngOnInit() {
        this.propertyService.getInitialFormData().subscribe((formInitData: FormInitDataModel) => {
            this.counties = formInitData.counties;
            this.propertyTypes = formInitData.propertyTypes;
            this.propertyStates = formInitData.propertyStates;
        });
        this.storage = JSON.parse(localStorage.getItem('user'));

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
                    this.editing = true;
                }
            },
            error => console.warn(error),
        );
    }


    getPropertyData = (id: string) => {
        this.propertyService.fetchPropertyData(id).subscribe(
            (response: PropertyFormDataModel) => {
                console.log(response);
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
                        imageUrl: [null],
                    },
                );
                this.actualUrlList = response.imageUrl;
                this.actualPublicIdList = response.publicId;
            },
            () => {},
            () => {
            },
        );
    };

    submit = () => {
        this.displayLoadingCircle = true;
        this.formData = {...this.propertyForm.value};
        //  console.log(this.formData);

        this.searchPosition = this.formData.zipCode + ' ' + this.formData.street + ' ' + this.formData.city + ' ' + this.formData.streetNumber;
        this.addressToDecode.address = this.searchPosition;

        this.getTheOtherFormData();

    };

    createNewProperty(data: PropertyFormDataModel) {
        const dataToSend = {...data};
        dataToSend.propertyType = this.propertyTypes.filter(propertyType => propertyType.displayName === data.propertyType)[0].name;
        dataToSend.propertyState = this.propertyStates.filter(propertyState => propertyState.displayName === data.propertyState)[0].name;
        dataToSend.county = this.counties.filter(county => county.displayName === data.county)[0].name;


        this.propertyService.createProperty(dataToSend).subscribe(
            () => {
                this.displayLoadingCircle = false;
                console.log('created');
                this.ngZone.run(() =>
                    this.router.navigate(['profil-list/', this.storage.userId]));
            },
            error => validationHandler(error, this.propertyForm),
        );
    }

    private updateProperty(data: PropertyFormDataModel) {
        const dataToSend = {...data};
        dataToSend.propertyType = this.propertyTypes.filter(propertyType => propertyType.displayName === data.propertyType)[0].name;
        dataToSend.propertyState = this.propertyStates.filter(propertyState => propertyState.displayName === data.propertyState)[0].name;
        dataToSend.county = this.counties.filter(county => county.displayName === data.county)[0].name;

        this.propertyService.updateProperty(dataToSend, this.propertyId).subscribe(
            () => {
                this.displayLoadingCircle = false;
                this.router.navigate(['profil-list/', this.storage.userId]);
            },
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

    getTheOtherFormData() {
        this.geocoder.geocode(this.addressToDecode,
            (results: google.maps.GeocoderResult[], status: google.maps.GeocoderStatus) => {
                if (status === google.maps.GeocoderStatus.OK) {
                    this.lngCoord = results[0].geometry.location.lng();
                    this.latCoord = results[0].geometry.location.lat();

                } else {
                    console.log(
                        'Geocoding service: geocode was not successful for the following reason: '
                        + status,
                    );
                    console.log(status + ' error');
                }

                this.formData.lngCoord = this.lngCoord;
                this.formData.latCoord = this.latCoord;

                //  console.log('latlong', this.latCoord, this.lngCoord);

                this.formData.isValid = true;

                if (this.selectedFile != null) {
                    this.imageService.uploadImage(this.selectedFile).subscribe(
                        (data) => {
                            this.answer = data;

                            this.answerPublicId[0] = this.answer[0];

                            if (this.actualPublicIdList.length < 1 || this.actualPublicIdList[0] === '') {
                                this.actualPublicIdList[0] = this.answer[0];
                            } else {
                                this.actualPublicIdList.push(this.answer[0]);
                            }

                            if (this.actualUrlList.length < 1 || this.actualUrlList[0] === '') {
                                this.actualUrlList[0] = this.answer[1];
                            } else {
                                this.actualUrlList.push(this.answer[1]);
                            }


                            this.formData.publicId = this.actualPublicIdList;
                            this.formData.imageUrl = this.actualUrlList;

                            this.propertyId ? this.formData.stateForAdmin = 'Approval after edit' :
                                this.formData.stateForAdmin = 'Approval after create';

                            this.selectedFile = null;
                        },
                        () => {},
                        () => {
                            console.log('COMPLETE', this.formData);
                            this.propertyId ? this.updateProperty(this.formData) : this.createNewProperty(this.formData);
                        },
                    );
                } else {
                    this.propertyId ? this.formData.stateForAdmin = 'Approval after edit' :
                        this.formData.stateForAdmin = 'Approval after create';
                    this.formData.publicId = this.actualPublicIdList;
                    this.formData.imageUrl = this.actualUrlList;
                    this.propertyId ? this.updateProperty(this.formData) : this.createNewProperty(this.formData);
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

    deletePicture = () => {
        this.router.navigate(['property-details/' + this.propertyId + '/images']);
    };

}
