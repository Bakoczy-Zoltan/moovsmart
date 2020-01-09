import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyListItemModel } from '../../models/propertyListItem.model';
import { PropertyService } from '../../services/property.service';


@Component({
    selector: 'app-property-list',
    templateUrl: './property-list.component.html',
    styleUrls: ['./property-list.component.css'],
})
export class PropertyListComponent implements OnInit {

    propertyListItemModels: Array<PropertyListItemModel>;
    defaultPicture = 'https://atasouthport.com/wp-content/uploads/2017/04/default-image.jpg';
    propertyTypes: PropertyTypeOptionModel[];
    propertyStates: PropertyStateOptionModel[];
    cities;
    id: string;

    constructor(private propertyService: PropertyService,
                private router: Router,
                private route: ActivatedRoute) {
    }

    ngOnInit() {

        this.route.paramMap.subscribe(
            () => {
                this.id = this.propertyService.userName2;
                console.log(this.id + ' my LIST');
                this.propertyService.getMyPropertyList(this.id).subscribe(
                    (datas: Array<PropertyListItemModel>) => {
                        this.propertyListItemModels = datas;
                    },
                );
            },
        );

        this.propertyService.getPropertyList().subscribe(
            propertyListItems => this.propertyListItemModels = propertyListItems,
        );
    }

    details(id: number) {
        this.router.navigate(['property-details', id]);
    }
}
