import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyService } from '../../services/property.service';

@Component({
    selector: 'app-delete-picture',
    templateUrl: './delete-picture.component.html',
    styleUrls: ['./delete-picture.component.css'],
})
export class DeletePictureComponent implements OnInit {

    pictureDetails: PictureListItemModel;
    imageUrls: string[];
    publicIds: string[];
    pictureIdToDelete: string;
    propertyId: number;

    constructor(private propertyService: PropertyService,
                private activatedRoute: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit() {
        this.activatedRoute.paramMap.subscribe(
            paramMap => {
                const idParam: number = +paramMap.get('id');
                if (isNaN(idParam)) {
                    this.router.navigate(['property-list']);
                } else {
                    this.propertyId = idParam;
                    this.propertyService.getPictures(idParam).subscribe(
                        pictureDetails => {
                            this.pictureDetails = pictureDetails;
                            this.imageUrls = this.pictureDetails.imageUrl;
                            this.publicIds = this.pictureDetails.publicId;
                        },
                        () => {
                            this.router.navigate(['property-details/' + paramMap + '/images']);
                        },
                    );
                }
            },
        );
    }

    deletePicture(index: number) {
        this.pictureIdToDelete = this.pictureDetails.publicId[index];
        this.imageUrls.splice(index, 1);
        this.publicIds.splice(index, 1);

        this.pictureDetails.imageUrl = this.imageUrls;
        this.pictureDetails.publicId = this.publicIds;

        this.propertyService.deletePicture(this.pictureIdToDelete,
            this.propertyId,
        ).subscribe(
            () => {

            },
            () => {},
            () => {
            this.propertyService.updatePictureList(this.pictureDetails, this.propertyId).subscribe(
                () => {
                    this.router.navigate(['property-details/' + this.propertyId + '/images']);
                }
            )
            },
        );
    }
}
