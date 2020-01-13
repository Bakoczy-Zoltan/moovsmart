import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FilteredListModel } from '../../models/FilteredListModel';
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
    cities: string[];

    id: string;
    registratedUser: boolean;
    filteredForm: FormGroup;
    filteredFormDatas: any;

    constructor(private propertyService: PropertyService,
                private router: Router,
                private route: ActivatedRoute) {
        this.filteredForm = new FormGroup(
            {
                'minPrice': new FormControl(0),
                'maxPrice': new FormControl(null),
                'minSize': new FormControl(0),
                'maxSize': new FormControl(null),
                'propertyState': new FormControl(null),
                'propertyType': new FormControl(null),
                'city': new FormControl(null),
                'numberOfRooms': new FormControl(null),
            },
        );

    }

    ngOnInit() {

        if (localStorage.getItem('user') != null) {
            this.registratedUser = true;
        }

        this.propertyService.getInitialFormData().subscribe((formInitData: FormInitDataModel) => {
            this.propertyTypes = formInitData.propertyTypes;
            this.propertyStates = formInitData.propertyStates;
        });

        this.propertyService.getCityList().subscribe(
            (data: string[]) => {
                this.cities = data;
                console.log(this.cities);
            },
        );

        this.propertyService.getPropertyList().subscribe(
            propertyListItems => this.propertyListItemModels = propertyListItems,
        );

    }

    details(id: number) {
        this.router.navigate(['property-details', id]);
    }

    filterProperties() {
        this.filteredFormDatas = this.filteredForm.value;
        this.sendFilterFieldList(this.filteredFormDatas);
    }

    private sendFilterFieldList(filteredFormDatas: FilteredListModel) {
        this.propertyService.sendFilterList(filteredFormDatas).subscribe(
            (filteredProperties) => this.propertyListItemModels = filteredProperties
        )
    }

}
