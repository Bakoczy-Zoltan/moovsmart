import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyDetailsModel } from '../../models/propertyDetails.model';
import { PropertyListItemModel } from '../../models/propertyListItem.model';
import { PropertyService } from '../../services/property.service';

@Component({
  selector: 'app-property-details',
  templateUrl: './property-details.component.html',
  styleUrls: ['./property-details.component.css']
})

export class PropertyDetailsComponent implements OnInit {
    defaultPicture = 'https://atasouthport.com/wp-content/uploads/2017/04/default-image.jpg';
    propertyDetails: PropertyDetailsModel;
    images: string[];

    constructor(private propertyService: PropertyService, private activatedRoute: ActivatedRoute,
                private router: Router) {

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
                            },
                            () =>
                                this.router.navigate(['property-list']),
                        )
                    ;
                }
            });
    }

    ngOnInit() {
    }

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
        this.router.navigate(['property-form', id]);
    }
}

