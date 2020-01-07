import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";

@Injectable({providedIn: 'root'})
export class ImageService {

    constructor(private http: HttpClient) {}


    public uploadImage(image: File): Observable<any> {
        const formData = new FormData();
            formData.append(image.name, image);
        return this.http.post(environment.apiUrl+'/images', formData);
    }
}
