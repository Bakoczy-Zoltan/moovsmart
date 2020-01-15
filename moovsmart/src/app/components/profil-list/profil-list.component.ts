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
    actualPageList: PropertyListItemModel[] = [];
    actualPageNumber: number;

    constructor(private propertyService: PropertyService,
                private route: ActivatedRoute,
                private router: Router) { }

    ngOnInit() {
        this.route.paramMap.subscribe(
            paramMap => {
                const userId = paramMap.get('id');
                if (userId) {
                    this.propertyService.getMyPropertyList(+userId).subscribe(
                        propertyListItems => {
                            this.propertyListItemModels = propertyListItems;
                            this.refactorOfPrice(this.propertyListItemModels);
                            this.actualPageNumber = 1;
                        },
                    );
                }
            },
        );
    }

    details(id: number) {
        this.router.navigate(['property-details', id]);
    }

    refactorOfPrice(datas: PropertyListItemModel[]){
        for(let i= 0; i < datas.length; i++){
            const property = datas[i];
            const formatedPrice = property.price / 1000000;
            property.price = +formatedPrice.toPrecision(3);
        }

    }
}
