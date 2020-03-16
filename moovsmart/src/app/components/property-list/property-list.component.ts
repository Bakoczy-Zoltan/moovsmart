import { applySourceSpanToExpressionIfNeeded } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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

    actualPageList: [PropertyListItemModel[]] = [[]];
    actualPageNumber: number;

    propertyTypes: PropertyTypeOptionModel[];
    propertyStates: PropertyStateOptionModel[];
    cities: string[];

    id: string;
    registratedUser: boolean;
    filteredForm: FormGroup;
    filteredFormDatas: any;
    needFilterList: boolean;
    filterOpenMessage: string;
    storage: any;
    emptyList: boolean;

    constructor(private propertyService: PropertyService,
                private router: Router,
                private route: ActivatedRoute) {

        this.clearFilterFields();
        this.needFilterList = false;
        this.emptyList = false;
        console.log("LISt comp")

    }

    clearFilterFields() {
        this.filteredForm = new FormGroup(
            {
                'minPrice': new FormControl(0, [Validators.min(0), Validators.pattern('^0$|^[1-9]+[0-9]*$')]),
                'maxPrice': new FormControl(null, [Validators.min(0), Validators.pattern('[1-9]+[0-9]*$')]),
                'minSize': new FormControl(0, [Validators.min(0) ,Validators.pattern('^0$|^[1-9]+[0-9]*$')]),
                'maxSize': new FormControl(null, [Validators.min(0), Validators.pattern('[1-9]+[0-9]*$')]),
                'propertyState': new FormControl(null),
                'propertyType': new FormControl(null),
                'city': new FormControl(null),
                'numberOfRooms': new FormControl(0, [Validators.min(0), Validators.max(12), Validators.pattern('^0$|^[1-9]|[[1][0-2]]')]),
            },
        );
    }
    ngOnInit() {

        this.filterOpenMessage = 'Szűrés';
        this.clearFilterFields();
        this.actualPageNumber = 1;

        this.refreshPropertyList();

        if (localStorage != null && localStorage.getItem('user') != null) {
            this.registratedUser = true;
            this.storage = JSON.parse(localStorage.getItem('user'));
        }

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
            (filteredProperties) => {
                this.propertyListItemModels = filteredProperties;
                this.actualPageList = this.makingActualList(this.propertyListItemModels);
                this.actualPageNumber = 1;
            },
        );
    }

    private makingActualList(propertyListItemModels: Array<PropertyListItemModel>) {
        const listSize = propertyListItemModels.length;
        const miniListSize = 5;
        let actualList: [PropertyListItemModel[]] = [[]];
        let tempList: PropertyListItemModel[] = [];

        let indexBig = 0;
        for (let i = 0; i < listSize; i++) {
            let indexMini = 1;
            const property = propertyListItemModels[i];
            const formatedPrice = property.price / 1000000;
            property.price = +formatedPrice.toPrecision(3);
            tempList.push(property);

            if (i!== 0 && ((i+1) % miniListSize) === 0) {
                actualList.push(tempList);
                indexBig++;
                tempList = [];
                indexMini = 0;
            }
            indexMini++;
        }
        actualList.push(tempList);
        actualList.shift();
        return actualList;
    }

    makeFilterBar() {
        if (this.needFilterList === false) {
            this.needFilterList = true;
            this.filterOpenMessage = 'Szűrés kikapcsolás';
            this.propertyService.getInitialFormData().subscribe((formInitData: FormInitDataModel) => {
                this.propertyTypes = formInitData.propertyTypes;
                this.propertyStates = formInitData.propertyStates;
            });

            this.propertyService.getCityList().subscribe(
                (data: string[]) => {
                    this.cities = data;
                },
            );
        } else {
            this.needFilterList = false;
            this.filterOpenMessage = 'Szűrés';
            this.refreshPropertyList();
        }
    }

    refreshPropertyList(){
        this.propertyService.getPropertyList().subscribe(
            propertyListItems => {
                this.propertyListItemModels = propertyListItems;
                this.emptyList = this.propertyListItemModels.length < 1;
                this.actualPageList = this.makingActualList(this.propertyListItemModels);

               // console.log(this.actualPageList);
            },
        );
        this.clearFilterFields();
    }

    pageLeft() {
        this.actualPageNumber--;
    }

    pageRight() {
        this.actualPageNumber++;
    }
}
