import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({providedIn: 'root'})
export class ImageService {

    baseUrl = environment.apiUrl + '/images';

    constructor(private http: HttpClient) {}

    public uploadImage(image: File): Observable<any> {
        const uploadData = new FormData();
        uploadData.append('myPicture', image);
        console.log(this.baseUrl + " service url");
        const imageServiceReturn = this.http.post(this.baseUrl, uploadData);
        return imageServiceReturn;
    }
}
