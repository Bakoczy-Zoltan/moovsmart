import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PropertyDetailsModel } from '../models/propertyDetails.model';
import { PropertyListItemModel } from '../models/propertyListItem.model';
import { PropertyFormDataModel } from '../models/propertyFormData.model';
import { UserFormDataModel } from '../models/userFormData.model';

@Injectable({
    providedIn: 'root',
})
export class PropertyService {

    baseUrl = 'http://localhost:8080/api/properties';
    baseUserUrl = 'http://localhost:8080/api/user';

    constructor(private httpClient: HttpClient) {
    }

    createProperty(roomFormData: PropertyFormDataModel): Observable<any> {
        return this.httpClient.post(this.baseUrl, roomFormData);
    }

    getPropertyList(): Observable<Array<PropertyListItemModel>> {
        return this.httpClient.get<Array<PropertyListItemModel>>(this.baseUrl);
    }

    getPropertyDetails = (id: number): Observable<PropertyDetailsModel> => {
        return this.httpClient.get<PropertyDetailsModel>(this.baseUrl + '/' + id);
    };


    updateProperty(data: PropertyFormDataModel, propertyId: number): Observable<any> {
        data.id = propertyId;
        return this.httpClient.put<any>(this.baseUrl + '/' + propertyId, data);
    }

    deleteProperty(id: number): Observable<any> {
        return this.httpClient.delete<any>(this.baseUrl + '/' + id);
    }

    fetchPropertyData(id: string): Observable<PropertyFormDataModel> {
        return this.httpClient.get<PropertyFormDataModel>(this.baseUrl + '/' + id);
    }

    registerUser(userFormData: UserFormDataModel): Observable<any> {
        return this.httpClient.post(this.baseUserUrl + '/registration', userFormData);
    }
}
