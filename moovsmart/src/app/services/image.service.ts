import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cloudinary } from '@cloudinary/angular-5.x';
import { FileUploader, FileUploaderOptions, ParsedResponseHeaders } from 'ng2-file-upload';

@Injectable({providedIn: 'root'})
export class ImageService {

    constructor(private http: HttpClient) {}


    public uploadImage(image: File): Observable<any> {
        const formData = new FormData();
            formData.append('image', image);
        return this.http.post('/api/images', formData);
    }
}