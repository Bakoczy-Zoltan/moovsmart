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
    registratedUser: boolean;

    constructor(private propertyService: PropertyService,
                private router: Router,
                private route: ActivatedRoute) {
    }

    ngOnInit() {

        if(localStorage.getItem('user') != null){
            this.registratedUser = true;
        }

        this.id = this.propertyService.userName2;
        this.propertyService.getPropertyList().subscribe(
            propertyListItems => this.propertyListItemModels = propertyListItems,
        );


        this.route.paramMap.subscribe(
            paramMap => {
                const editableId = paramMap.get('id');
                if (editableId) {

                    this.propertyService.getMyPropertyList(this.id).subscribe(
                        (datas: Array<PropertyListItemModel>) => {
                            this.propertyListItemModels = datas;
                        },
                    );
                } else{
                    this.propertyService.getPropertyList().subscribe(
                        propertyListItems => this.propertyListItemModels = propertyListItems,
                    );
                }
            },
        );


    }

    details(id: number) {
        this.router.navigate(['property-details', id]);
    }
}
