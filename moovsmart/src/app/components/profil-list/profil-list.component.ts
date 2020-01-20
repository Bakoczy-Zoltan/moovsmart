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

    constructor(private propertyService: PropertyService,
                private route: ActivatedRoute,
                private router: Router) { }

    ngOnInit() {
        this.actualPageNumber = 1;
        this.storage = JSON.parse(localStorage.getItem('user'));
        this.route.paramMap.subscribe(
            paramMap => {
                const userId = paramMap.get('id');
                if (userId) {
                    this.refreshPropertyList(+userId);
                }
            },
        );
    }

    refreshPropertyList(usId: number){
        this.propertyService.getMyPropertyList(usId).subscribe(
            propertyListItems => {
                this.propertyListItemModels = propertyListItems;
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

    refactorOfPrice(datas: PropertyListItemModel[]) {
        for (let i = 0; i < datas.length; i++) {
            const property = datas[i];
            const formatedPrice = property.price / 1000000;
            property.price = +formatedPrice.toPrecision(3);
        }

    }

    pageLeft() {
        this.actualPageNumber--;
    }

    pageRight() {
        this.actualPageNumber++;
    }
}
