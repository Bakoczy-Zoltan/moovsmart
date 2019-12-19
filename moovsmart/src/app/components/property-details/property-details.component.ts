import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-property-details',
  templateUrl: './property-details.component.html',
  styleUrls: ['./property-details.component.css']
})

export class PropertyDetailsComponent implements OnInit {
  propertyImage : any[];

  constructor() {
    const img1 = "https://q-cf.bstatic.com/images/hotel/max1024x768/195/195846764.jpg";
    const img2 =  "https://r-cf.bstatic.com/images/hotel/max1024x768/151/151787518.jpg";

    this.propertyImage = [img1, img2, img1];
  }

  ngOnInit() {
  }

  openModal() {

  }

  currentSlide(i: number) {

  }

  closeModal() {

  }

  plusSlides(number: number) {

  }
}
