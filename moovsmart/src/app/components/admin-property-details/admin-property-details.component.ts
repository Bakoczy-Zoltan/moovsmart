import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyService } from '../../services/property.service';

@Component({
    selector: 'app-admin-property-details',
    templateUrl: './admin-property-details.component.html',
    styleUrls: ['./admin-property-details.component.css'],
})
export class AdminPropertyDetailsComponent implements OnInit {

    defaultPicture = 'https://atasouthport.com/wp-content/uploads/2017/04/default-image.jpg';
    images: string[];
    propertyDetails: any;
    
    display = 'none';
    
    propertyToDelete: number;

    constructor(private propertyService: PropertyService,
                private activatedRoute: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit() {
        this.activatedRoute.paramMap.subscribe(
            paramMap => {
                const idParam: number = +paramMap.get('id');
                if (isNaN(idParam)) {
                    this.router.navigate(['admin']);
                } else {this.propertyService
                        .getPropertyDetailsForApproval(idParam)
                        .subscribe(
                            proDetails => {
                                this.propertyDetails = proDetails;

                                const formatedPrice = this.propertyDetails.price / 1000000;
                                this.propertyDetails.price = +formatedPrice.toPrecision(3);

                                this.images = this.propertyDetails.imageUrl;

                                if (this.images !== null) {
                                    this.changeDefaultImg(this.images[0]);
                                }
                            },
                            () =>
                            this.router.navigate(['admin']),
                        )
                }
            }
        )
    }


    changeDefaultImg(image: string) {
        if (image !== undefined && image !== null && image.length > 3) {
            this.defaultPicture = image;
        } else {
            this.images[0] = this.defaultPicture;
        }
    }

  closeDial() {
    this.display = 'none';
  }

  delete(propertyToDelete: number) {
    this.propertyService.setPropertyToForbidden(propertyToDelete).subscribe(
        () => {
            this.router.navigate(['admin']);
        }
    );
  }

    approve(id: number) {
        this.propertyService.setPropertyToAccepted(id).subscribe(
            () => {
                this.router.navigate(['admin']);
            }
        );

    }

    askingForSure(id: any) {
        this.propertyToDelete = id;
        this.display = 'block';
    }
}
