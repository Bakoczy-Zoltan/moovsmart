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

    constructor(private http: HttpClient,
                private router: Router,
                private propertyService: PropertyService) {
        this.BASE_URL = 'http://localhost:8080';
    }


    ngOnInit() {
        if(localStorage.getItem('user')){
            this.registratedUser = true;
        }
        this.propertyService.userName.subscribe(
            (name)=> {
                if(name != null){
                    this.registratedUser = true;
                }
            })
    }

    logout() {
        this.registratedUser = false;
        this.http.post(this.BASE_URL + '/logout', {}).subscribe(() => {
            localStorage.removeItem('user');
            this.propertyService.userName.next(null);
            this.router.navigateByUrl('/');
        });
    }
}
