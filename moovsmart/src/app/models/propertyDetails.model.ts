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
    // street: string;
    city: string;
    // searchPosition: string;
    lngCoord: number;
    latCoord: number;

    description: string;
    imageUrl: string[];
}
