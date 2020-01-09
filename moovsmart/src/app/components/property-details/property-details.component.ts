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
    propertyDetails: PropertyDetailsModel;
    images: string[];
    map: google.maps.Map;
    // lat = 47.545182;
    // lng = 19.0419057;
    lat: number = 0;
    lng: number = 0;

    coordinates: google.maps.LatLng;
    mapOptions: google.maps.MapOptions;
    marker: google.maps.Marker;

    constructor(private propertyService: PropertyService,
                private activatedRoute: ActivatedRoute,
                private router: Router) {

        // this.coordinates = new google.maps.LatLng(this.lat, this.lng);

        this.activatedRoute.paramMap.subscribe(
            paramMap => {
                const idParam: number = +paramMap.get('id');
                if (isNaN(idParam)) {
                    this.router.navigate(['property-list']);
                } else {
                    this.propertyService
                        .getPropertyDetails(idParam)
                        .subscribe(
                            proDetails => {
                                this.propertyDetails = proDetails;
                                this.images = this.propertyDetails.imageUrl;
                                this.changeDefaultImg(this.images[0]);

                                 this.lat = this.propertyDetails.latCoord;
                                 this.lng = this.propertyDetails.lngCoord;

                                //   this.coordinates = new google.maps.LatLng(this.lat, this.lng);

                            },
                            () =>
                                this.router.navigate(['property-list']),
                        )
                    ;
                }
            });
    }

    ngOnInit() {
        console.log(this.lat + " in INIT");
        console.log(this.lng);
        this.coordinates = new google.maps.LatLng(this.lat, this.lng);
        this.mapOptions = {
            center: this.coordinates,
            zoom: 15,
        };
    }

    ngAfterViewInit() {
        this.mapInitializer();
    }

    mapInitializer() {
        this.coordinates = new google.maps.LatLng(this.lat, this.lng);
        this.mapOptions = {
            center: this.coordinates,
            zoom: 15,
        };

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
        if (image !== undefined && image !== null) {
            this.defaultPicture = image;
        }
    }

    goBack() {
        this.router.navigate(['property-list']);
    }

    delete(id: number) {
        this.propertyService.deleteProperty(id).subscribe(
            () => {
                this.router.navigate(['property-list']);
            },
            error => console.warn(error),
        );
    }

    edit(id: number) {
        this.router.navigate(['property-form/', id]);
    }
}

