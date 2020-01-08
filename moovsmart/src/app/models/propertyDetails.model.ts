export interface PropertyDetailsModel {
    id: number;
    name: string;
    numberOfRooms: number;
    price: number;
    buildingYear: number;
    area: number;
    propertyType: string;
    propertyState: string;
    county: string;
    zipCode: number;
//    street: string;
    searchPosition: string;

    description: string;
    imageUrl: string[];
}
