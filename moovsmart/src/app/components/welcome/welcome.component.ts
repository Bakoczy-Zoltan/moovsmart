import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyService } from '../../services/property.service';

@Component({
    selector: 'app-welcome',
    templateUrl: './welcome.component.html',
    styleUrls: ['./welcome.component.css'],
})
export class WelcomeComponent implements OnInit {

    constructor(private propertyService: PropertyService,
                private route: ActivatedRoute,
                private router: Router) {}

    ngOnInit() {
        this.route.paramMap.subscribe(paramMap => {
                const idParam = paramMap.get('id');
                console.log("ID user: " + paramMap.get('id'));
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
}
