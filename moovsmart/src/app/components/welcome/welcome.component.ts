import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyService } from '../../services/property.service';

@Component({
    selector: 'app-welcome',
    templateUrl: './welcome.component.html',
    styleUrls: ['./welcome.component.css'],
})
export class WelcomeComponent implements OnInit {
    storage: any;
    registratedUser: boolean;
    userId: string;

    constructor(private propertyService: PropertyService,
                private route: ActivatedRoute,
                private router: Router) {}

    ngOnInit() {
        this.route.paramMap.subscribe(paramMap => {
                const idParam = paramMap.get('id');
                if (idParam) {
                    this.validateUser(idParam);
                }
            },
        );
    };

    validateUser(id: string) {
        this.propertyService.validateUser(id).subscribe(
            (data: string) => {
                localStorage.setItem('user', data);
            },
            (err) => console.log(err),
        );
    }

    signIn() {
        this.checkUser();
        if (this.registratedUser !== true) {
            this.router.navigate(['signin']);
        } else {
            this.router.navigate(['profil-list', this.userId]);
        }
    }

    checkUser() {
        if (localStorage != null && localStorage.getItem('user') != null) {
            this.registratedUser = true;
            this.storage = JSON.parse(localStorage.getItem('user'));
            this.userId = this.storage.userId;
        } else {
            this.userId = null;
            this.registratedUser = false;
        }
    }
}
