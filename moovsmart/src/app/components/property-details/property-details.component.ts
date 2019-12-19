import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { isNull } from 'util';
import { PropertyDetailsModel } from '../../models/propertyDetails.model';
import { PropertyService } from '../../services/property.service';

@Component({
    selector: 'app-property-details',
    templateUrl: './property-details.component.html',
    styleUrls: ['./property-details.component.css'],
})

export class PropertyDetailsComponent implements OnInit {


    propertyImage: any[];
    propertyDetails: PropertyDetailsModel;

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
                            proDetails => this.propertyDetails = proDetails,
                            () => this.router.navigate(['property-list']),
                        );
                }
            });
    }

    ngOnInit(): void {

    }


    openModal() {

    }

    currentSlide(i: number) {
    }

    closeModal() {
    }

    plusSlides(number: number) {
    }
}

