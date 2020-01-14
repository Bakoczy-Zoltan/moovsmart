import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { FilteredListModel } from '../models/FilteredListModel';
import { PropertyDetailsModel } from '../models/propertyDetails.model';
import { PropertyListItemModel } from '../models/propertyListItem.model';
import { PropertyFormDataModel } from '../models/propertyFormData.model';
import { UserFormDataModel } from '../models/userFormData.model';
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root',
})
export class PropertyService {

    userName = new Subject<string>();
    userName2 : string;
    registratedUser: boolean;
    regisTrated = new Subject<boolean>();

    baseUrl = environment.apiUrl+'/properties';
    baseUserUrl = environment.apiUrl+'/user';
    url = 'localhost:8080/api/properties';

    constructor(private httpClient: HttpClient) {
    }

    getInitialFormData(): Observable<FormInitDataModel> {
        return this.httpClient.get<FormInitDataModel>(this.baseUrl + '/formData');
    }

    createProperty(roomFormData: PropertyFormDataModel): Observable<any> {
        return this.httpClient.post(this.baseUrl + "/authUser", roomFormData);
    }

    getPropertyList(): Observable<Array<PropertyListItemModel>> {
        return this.httpClient.get<Array<PropertyListItemModel>>(this.baseUrl);
    }

    getPropertyDetails = (id: number): Observable<PropertyDetailsModel> => {
        return this.httpClient.get<PropertyDetailsModel>(this.baseUrl + '/' + id);
    };


    updateProperty(data: PropertyFormDataModel, propertyId: number): Observable<any> {
        data.id = propertyId;
        return this.httpClient.put<any>(this.baseUrl + '/authUser/' + propertyId, data);
    }

    deleteProperty(id: number): Observable<any> {
        return this.httpClient.delete<any>(this.baseUrl + '/authUser/' + id);
    }

    fetchPropertyData(id: string): Observable<PropertyFormDataModel> {
        return this.httpClient.get<PropertyFormDataModel>(this.baseUrl + '/authUser/' + id);
    }

    registerUser(userFormData: UserFormDataModel): Observable<any> {
        return this.httpClient.post(this.baseUserUrl + '/registration', userFormData);
    }

    validateUser(id: string): Observable<any> {
        return this.httpClient.get<any>(this.baseUserUrl + '/validuser/' + id);
    }

    signIn(credentials: any):Observable<any> {
        const headers = new HttpHeaders( credentials? {
            authorization: 'Basic ' + btoa(credentials.userName + ':' + credentials.password),
        } : {});
        return this.httpClient.get<any>(this.baseUserUrl + '/me', {headers:headers})
    }

    getMyPropertyList(id: string): Observable<Array<PropertyListItemModel>> {
        return this.httpClient.get<Array<PropertyListItemModel>>(this.baseUrl + '/authUser/myList');
    }

    getCityList(): Observable<string[]> {
        return this.httpClient.get<string[]>(this.baseUrl + '/getCityList');
    }

    sendFilterList(datas: FilteredListModel): Observable<Array<PropertyListItemModel>> {
        return this.httpClient.post<Array<PropertyListItemModel>>(this.baseUrl + '/filteredList', datas);
    }

    getPictures(idParam: number): Observable<PictureListItemModel> {
        return this.httpClient.get<PictureListItemModel>(this.baseUrl + '/' + idParam + '/images');
    }

    deletePicture(pictureIdToDelete: string, propertyId: number): Observable<any> {
        return this.httpClient.post<any>(this.baseUrl + '/' + propertyId + '/images', pictureIdToDelete);
    }

    updatePictureList(imageList: PictureListItemModel, propertyId: number): Observable<any> {
        return this.httpClient.post<any>(this.baseUrl + '/images/' + propertyId, imageList);
    }
}
