import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyDetailsModel } from '../../models/propertyDetails.model';
import { PropertyService } from '../../services/property.service';

@Component({
    selector: 'app-property-details',
    templateUrl: './property-details.component.html',
    styleUrls: ['./property-details.component.css'],
})

export class PropertyDetailsComponent implements OnInit {


    defaultPicture = 'https://atasouthport.com/wp-content/uploads/2017/04/default-image.jpg';
    propertyDetails: PropertyDetailsModel;

    images = ['https://www.cartoonnetworkhotel.com/sites/cnhotel.com/files/pcore_tiers/Cartoon%20Network%20Hotel_Room-AdventureTime.jpg',
        'https://www.danubiushotels.com/w/accomms/0_1000/0/rooms/Palatinus-Economy-Room-midi1775.jpg',
        'https://www.icehotel.com/sites/cb_icehotel/files/styles/image_column_large/public/Kaamos-Johan-Broberg.jpg?h=3c9275bd&itok=gHToh0qO'];

    constructor(private propertyService: PropertyService, private activatedRoute: ActivatedRoute,
                private router: Router) {

        this.defaultPicture = this.images[0];


        this.activatedRoute.paramMap.subscribe(
            paramMap => {
                const idParam: number = +paramMap.get('id');
                if (isNaN(idParam)) {
                    this.router.navigate(['property-list']);
                } else {
                    this.propertyService
                        .getPropertyDetails(idParam)
                        .subscribe(
                            proDetails => this.propertyDetails = proDetails,
                            () => this.router.navigate(['property-list']),
                        );
                }
            });
    }

    ngOnInit() {
    }


    changeDefaultImg(image: string) {
        this.defaultPicture = image;
    }

    goBack() {
        this.router.navigate(['property-list']);
    }
}

