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
    id: number;

    constructor(private http: HttpClient,
                private router: Router,
                private propertyService: PropertyService) {

        this.BASE_URL = 'http://localhost:8080';
        this.registratedUser = (localStorage.getItem('user')!== null);
    }


    ngOnInit() {
        this.registratedUser = (localStorage.getItem('user')!== null);
        this.id = this.propertyService.userId;
        this.propertyService.regisTrated.subscribe(
            name => this.registratedUser = name
        )
    }

    logout() {
        this.registratedUser = false;
        this.http.post(this.BASE_URL + '/logout', {}).subscribe(() => {
            localStorage.removeItem('user');
            this.router.navigateByUrl('/');
            this.propertyService.regisTrated.next(false);
            this.propertyService.userId = null;
        });
    }

    ownProperties() {
        this.id = this.propertyService.userId;
        console.log("Nav User " + this.id);
        if(this.id !== null){
            this.router.navigate(['profil-list', this.id])
        }
    }
}
