import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
    BASE_URL: string;

    constructor(private http: HttpClient, private router: Router) {
        this.BASE_URL = 'http://localhost:8080';
    }


    ngOnInit() {
    }

    logout() {
        this.http.post(this.BASE_URL + '/logout', {}).subscribe(() => {
            localStorage.removeItem('user');
            this.router.navigateByUrl('/');
        });
    }
}
