import {Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyService } from '../../services/property.service';

@Component({
  selector: 'app-property-details',
  templateUrl: './property-details.component.html',
  styleUrls: ['./property-details.component.css']
})
export class PropertyDetailsComponent implements OnInit {

  propertyImage: any[];
  propertyDetails: PropertyDetailsModel;

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

  ngOnInit() {
  }

}
