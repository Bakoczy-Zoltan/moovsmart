import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class ImageService {

    constructor(private http: HttpClient) {}


    public uploadImage(images: File[]): Observable<any> {
        const formData = new FormData();

        images.forEach(function (image) {
            formData.append('image', image);
        });

        return this.http.post('/api/v1/image-upload', formData);
    }
}