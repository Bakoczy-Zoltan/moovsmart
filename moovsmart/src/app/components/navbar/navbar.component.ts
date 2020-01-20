import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PropertyService } from '../../services/property.service';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
    BASE_URL: string;
    registratedUser: boolean;
    id: number = null;
    userName: string;
    storage: any;

    constructor(private http: HttpClient,
                private router: Router,
                private propertyService: PropertyService) {

        this.BASE_URL = 'http://localhost:8080';
        this.registratedUser = (localStorage.getItem('user')!== null);
    }


    ngOnInit() {
        this.registratedUser = (localStorage.getItem('user')!== null);
        this.storage = JSON.parse(localStorage.getItem('user'));

        if(this.storage != null){
            this.userName = this.storage.name;
            this.id = this.storage.userId;
        }

       // this.id = this.propertyService.userId;
        this.propertyService.regisTrated.subscribe(
            name => this.registratedUser = name
        );
        this.propertyService.userName.subscribe(
            name => this.userName = name
        );
    }

    logout() {
        this.registratedUser = false;
        this.http.post(this.BASE_URL + '/logout', {}).subscribe(() => {
            localStorage.removeItem('user');
            this.router.navigateByUrl('/');
            this.propertyService.regisTrated.next(false);
            this.propertyService.userId = null;
            this.propertyService.userName.next(
                null
            )
        });

    }

    ownProperties() {
        this.storage = JSON.parse(localStorage.getItem('user'));
        if(this.storage != null){
            this.id = this.storage.userId;
        }
        if(this.id !== null && this.id !== undefined){
            this.router.navigate(['profil-list', this.id])
        }else{
            this.registratedUser = false;
        }
    }
}
