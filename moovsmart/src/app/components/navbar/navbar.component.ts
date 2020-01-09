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
    id: string;

    constructor(private http: HttpClient,
                private router: Router,
                private propertyService: PropertyService) {

        this.BASE_URL = 'http://localhost:8080';
        this.registratedUser = (localStorage.getItem('user')!== null);

    }


    ngOnInit() {
        this.registratedUser = (localStorage.getItem('user')!== null);
        this.propertyService.regisTrated.subscribe(
            name => this.registratedUser = name
        )
    }

    logout() {
        this.registratedUser = false;
        this.http.post(this.BASE_URL + '/logout', {}).subscribe(() => {
            localStorage.removeItem('user');
            this.router.navigateByUrl('/');
           // this.propertyService.registratedUser = false;
            this.propertyService.regisTrated.next(false);
        });
    }

    ownProperties() {
        if(this.id !== null){
            this.router.navigate(['property-list', 'ownList'])
        }
    }
}
