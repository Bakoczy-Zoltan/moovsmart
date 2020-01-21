import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyDetailsModel } from '../../models/propertyDetails.model';
import { PropertyService } from '../../services/property.service';

@Component({
    selector: 'app-property-details',
    templateUrl: './property-details.component.html',
    styleUrls: ['./property-details.component.css'],
})

export class PropertyDetailsComponent implements OnInit, AfterViewInit {

    defaultPicture = 'https://atasouthport.com/wp-content/uploads/2017/04/default-image.jpg';
    propertyDetails: any;
    images: string[];
    reagistratedUser: boolean;
    map: google.maps.Map;
    lat: number;
    lng: number;

    coordinates: google.maps.LatLng;
    mapOptions: google.maps.MapOptions;
    marker: google.maps.Marker;

    display = 'none';
    propertyToDelete: number;
    storage:any;

    constructor(private propertyService: PropertyService,
                private activatedRoute: ActivatedRoute,
                private router: Router) {

        this.propertyService.userName.subscribe(
            (name) => {
                this.checkValidUser(name);
            },
        );
    }

    ngOnInit() {
        this.activatedRoute.paramMap.subscribe(
            paramMap => {
                const idParam: number = +paramMap.get('id');
                if (isNaN(idParam)) {
                    console.log("Inside a detail");
                    this.router.navigate(['property-list']);

                } else {
                    this.propertyService
                        .getPropertyDetails(idParam)
                        .subscribe(
                            proDetails => {
                                this.propertyDetails = proDetails;

                                const formatedPrice = this.propertyDetails.price / 1000000;
                                this.propertyDetails.price = +formatedPrice.toPrecision(3);
                                this.checkOwnerOfProperty(this.propertyDetails);
                                this.images = this.propertyDetails.imageUrl;

                                if (this.images !== null) {
                                    this.changeDefaultImg(this.images[0]);
                                }

                                this.lat = this.propertyDetails.latCoord;
                                this.lng = this.propertyDetails.lngCoord;
                                this.coordinates = new google.maps.LatLng(this.lat, this.lng);

                                this.mapOptions = {
                                    center: this.coordinates,
                                    zoom: 15,
                                };
                                this.mapInitializer();
                            },
                            () =>
                                this.router.navigate(['property-list']),
                        )
                    ;
                }
            });

    }

    checkOwnerOfProperty(data: PropertyDetailsModel){
        this.reagistratedUser = (localStorage.getItem('user') !== null);
        this.storage = JSON.parse(localStorage.getItem('user'));

        if(this.storage != null && this.propertyDetails.userId != null){
            console.log(this.propertyDetails.userId + " owner ID");
            this.reagistratedUser = this.storage.userId == this.propertyDetails.userId;
        }else{
            this.reagistratedUser = false;
        }
    }

    checkValidUser(name: string) {
        if (name == null) {
            this.reagistratedUser = false;
        } else {
            this.reagistratedUser = true;
        }
    }

    ngAfterViewInit() {
        // this.mapInitializer();
    }

    mapInitializer() {
        this.map = new google.maps.Map(this.gmap.nativeElement,
            this.mapOptions);
        this.marker = new google.maps.Marker({
            position: this.coordinates,
            map: this.map,
            title: 'Ingatlan környéke',
            icon: {
                path: google.maps.SymbolPath.CIRCLE,
                scale: 55,
                fillColor: '#F00',
                fillOpacity: 0.4,
                strokeWeight: 0.4,
            },
        });
        this.marker.setMap(this.map);
    }

    @ViewChild('mapContainer', {static: false}) gmap: ElementRef;


    changeDefaultImg(image: string) {
        if (image !== undefined && image !== null && image.length > 3) {
            this.defaultPicture = image;
        } else {
            this.images[0] = this.defaultPicture;
        }
    }

    delete(id: number) {
        this.propertyService.deleteProperty(id).subscribe(
            () => {
                const userId = this.storage.userId;
                this.router.navigate(['profil-list/', userId]);
            },
            error => console.warn(error),
        );
    }

    edit(id: number) {
        this.router.navigate(['property-form/', id]);
    }

    openConfirmation(id: number) {
        this.propertyToDelete = id;
        this.display = 'block';
    }

    closeDial() {
        this.display = 'none';
    }
}

