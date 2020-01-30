import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyListItemModel } from '../../models/propertyListItem.model';
import { PropertyService } from '../../services/property.service';

@Component({
    selector: 'app-profil-list',
    templateUrl: './profil-list.component.html',
    styleUrls: ['./profil-list.component.css'],
})
export class ProfilListComponent implements OnInit {

    propertyListItemModels: PropertyListItemModel[] = [];
    defaultPicture = 'https://atasouthport.com/wp-content/uploads/2017/04/default-image.jpg';
    actualPageNumber: number;
    storage: any;
    emptyList: boolean;
    actualPageList: [PropertyListItemModel[]] = [[]];
    queryStatus: boolean = true;

    constructor(private propertyService: PropertyService,
                private route: ActivatedRoute,
                private router: Router) {
        this.emptyList = false;
    }

    ngOnInit() {
        this.queryStatus = true;
        this.actualPageNumber = 1;
        this.storage = JSON.parse(localStorage.getItem('user'));
        this.route.paramMap.subscribe(
            paramMap => {
                const userId = paramMap.get('id');
                if (userId) {
                    this.refreshPropertyList();
                }
            },
        );
    }

    refreshPropertyList(){
        this.queryStatus = false;
        this.propertyService.getMyPropertyList().subscribe(
            propertyListItems => {
                this.propertyListItemModels = propertyListItems;
                this.emptyList = this.propertyListItemModels.length < 1;
                this.actualPageList = this.makingActualList(this.propertyListItemModels);
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

    details(id: number) {
        this.router.navigate(['property-details', id]);
    }


    pageLeft() {
        this.actualPageNumber--;
    }

    pageRight() {
        this.actualPageNumber++;
    }

    getHoldingList() {
        this.queryStatus = true;
        this.actualPageNumber = 1;
        this.propertyService.getMyHoldingPropertyList().subscribe(
            dataList => {
                this.propertyListItemModels = dataList;
                this.actualPageList = this.makingActualList(this.propertyListItemModels);
            }
        )

    }
}
