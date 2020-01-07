import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PropertyDetailsModel } from '../models/propertyDetails.model';
import { PropertyListItemModel } from '../models/propertyListItem.model';
import { PropertyFormDataModel } from '../models/propertyFormData.model';
import { UserFormDataModel } from '../models/userFormData.model';
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root',
})
export class PropertyService {

    baseUrl = environment.apiUrl+'/properties';
    baseUserUrl = environment.apiUrl+'/user';

    constructor(private httpClient: HttpClient) {
    }

    getInitialFormData(): Observable<FormInitDataModel> {
        return this.httpClient.get<FormInitDataModel>(this.baseUrl + '/formData');
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

    validateUser(id: string): Observable<any> {
        return this.httpClient.get<any>(this.baseUserUrl + '/validuser/' + id);
    }
}
