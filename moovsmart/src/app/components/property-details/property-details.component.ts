import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { PropertyDetailsModel } from '../../models/propertyDetails.model';
import { PropertyService } from '../../services/property.service';

@Component({
    selector: 'app-property-details',
    templateUrl: './property-details.component.html',
    styleUrls: ['./property-details.component.css'],
})

export class PropertyDetailsComponent implements OnInit {


    propertyImage: any[];
    propertyDetails: Observable<PropertyDetailsModel>;

    constructor(private propertyService: PropertyService, private activatedRoute: ActivatedRoute,
                private router: Router) {
      this.activatedRoute.paramMap.subscribe(
          paramMap => {
            const idParam: number = +paramMap.get('id');
            if (isNaN(idParam) || idParam < 1) {
              this.router.navigate(['property-list']);
            } else {
              this.propertyDetails = this.propertyService.getPropertyDetails(idParam);
            }
          });
    }

      ngOnInit(): void {
        throw new Error("Method not implemented.");
    }

        openModal()
        {

        }

        currentSlide(i: number){
        }

        closeModal() {
        }

        plusSlides(number: number){
        }
    }

