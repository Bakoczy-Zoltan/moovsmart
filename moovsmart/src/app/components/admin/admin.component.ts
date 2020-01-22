import { Component, OnInit } from '@angular/core';
import { PropertyListItemModel } from '../../models/propertyListItem.model';
import { Router } from '@angular/router';
import { UserDetailsModel } from '../../models/userDetails.model';
import { UserFormDataModel } from '../../models/userFormData.model';
import { PropertyService } from '../../services/property.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {

    propertyListItemModels: Array<PropertyListItemModel>;
    buttonPushed: number;
    defaultPicture = 'https://atasouthport.com/wp-content/uploads/2017/04/default-image.jpg';
    images: string[];
    actualPageNumber: number;
    actualPageList: [PropertyListItemModel[]] = [[]];
    formData: any;
    userForHandling: UserDetailsModel;
    display = 'none';
    displayBan = 'none';
    userIdForBan: number;


    dateForm = this.formBuilder.group({
        'dateFrom': [''],
        'dateTo': [''],
    });

    emailForm = this.formBuilder.group({
        'userEmail': [''],
    });

    constructor(private propertyService: PropertyService,
                private router: Router,
                private formBuilder: FormBuilder) { }

    ngOnInit() {
        this.buttonPushed = 0;
        this.actualPageNumber = 1;
    }

    moveToApproval() {
        if (this.buttonPushed === 1) {
            this.buttonPushed = 0;
        } else {
            this.buttonPushed = 1;
            this.refreshPropertyList();
        }
    }

    moveToUserHandling() {
        if (this.buttonPushed === 2) {
            this.buttonPushed = 0;
        } else {
            this.buttonPushed = 2;
            this.propertyListItemModels = [];
        }
    }

    moveToArchived() {
        if (this.buttonPushed === 3) {
            this.buttonPushed = 0;
        } else {
            this.buttonPushed = 3;
            this.propertyListItemModels = [];
        }
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

            if (i !== 0 && ((i + 1) % miniListSize) === 0) {
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

    pageLeft() {
        this.actualPageNumber--;
    }

    pageRight() {
        this.actualPageNumber++;
    }

    details(id: number) {
        this.router.navigate(['admin/details', id]);
    }

    refreshPropertyList() {
        this.propertyService.getPropertyListForApproval().subscribe(
            propertyListItems => {
                this.propertyListItemModels = propertyListItems;
                this.actualPageList = this.makingActualList(this.propertyListItemModels);
            },
        );
    }


    submit = () => {
        this.formData = {...this.dateForm.value};
        this.propertyService.getArchivedProperties(this.formData).subscribe(
            propertyListItems => {
                this.propertyListItemModels = propertyListItems;
                this.actualPageList = this.makingActualList(this.propertyListItemModels);
            },
        );
    };


    submitEmail = () => {
        this.formData = {...this.emailForm.value};
        this.propertyService.getUserByMail(this.formData.userEmail).subscribe(
            user => {
                this.userForHandling = user;
                this.display = 'block';
            },
        );
    };


    listPropertiesOfUser(mail: string) {
        this.propertyService.getPropertyListByMail(mail).subscribe(
            propertyList => {
                this.propertyListItemModels = propertyList;
                this.actualPageList = this.makingActualList(this.propertyListItemModels);
                console.log("Property list by mail succeeded.")
            },
            () => {
                console.warn("Error occured during property list request.")
            }
        );
    }


    askForBanUser(id: number) {
        this.displayBan = 'block';
        this.userIdForBan = id;
    }


    banUser(userIdForBan: number) {
        this.displayBan = 'none';
        this.propertyService.banUser(userIdForBan).subscribe(
            () => {
                this.buttonPushed = 0;
                this.userIdForBan = null;
                this.userForHandling = null;
                this.display = 'none';
                console.log('User with id ' + userIdForBan + ' banned.');
            },
            () => {
                console.warn('Ban wasn\'t successful.');
            },
        );
    }

    unBanUser(id: number) {
        this.propertyService.unBanUser(id).subscribe(
            () => {
                this.buttonPushed = 0;
                this.userIdForBan = null;
                this.userForHandling = null;
                this.display = 'none';
                console.log('Unbanning was successful.');
            },
            () => {
                console.warn('Unban wasn\'t successful.');
            },
        );
    }

    closeDial() {
        this.displayBan = 'none';
    }

    reactivateProperty = (id: number) => {
        this.propertyService.reactivateProperty(id).subscribe(
            () => {

                this.propertyService.refreshArchivedPropertyList().subscribe(
                    propertyListItems => {
                        this.propertyListItemModels = propertyListItems;
                        this.actualPageList = this.makingActualList(this.propertyListItemModels);
                        console.log("Refreshing archived properties");
                    },
                    () => {
                        console.warn("Error during refresh archives");
                    }
                );


                console.log('Reactivation successful!');
            },
            () => {
                console.warn('Reactivation failed.');
            },
        );
    };

}
