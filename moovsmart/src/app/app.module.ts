import { HttpClientModule} from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { PropertyDetailsComponent } from './components/property-details/property-details.component';
import { PropertyFormComponent } from './components/property-form/property-form.component';
import { PropertyListComponent } from './components/property-list/property-list.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { WelcomeComponent } from './components/welcome/welcome.component';

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent,
        PropertyFormComponent,
        PropertyDetailsComponent,
        PropertyListComponent,
        RegistrationComponent,
        WelcomeComponent,
    ],
    imports: [
        HttpClientModule,
        ReactiveFormsModule,
        BrowserModule,
        AppRoutingModule,
    ],
    providers: [],
    bootstrap: [AppComponent],
})
export class AppModule {
}
