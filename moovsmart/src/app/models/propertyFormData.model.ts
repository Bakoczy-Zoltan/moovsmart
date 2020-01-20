export interface PropertyFormDataModel {
  id?: number;
  name: string;
  numberOfRooms: number;
  price: number;
  buildingYear: number;
  area: number;

  propertyType: string;
  propertyState: string;

  county: string;
  city: string;
  zipCode: number;
  street: string;
  streetNumber: string;
  description: string;
  imageUrl: string[];
  publicId: string[];

  owner: string;
  status: string;

  lngCoord: number;
  latCoord: number;

}
