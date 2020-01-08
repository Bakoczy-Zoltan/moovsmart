import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PropertyDetailsComponent } from './components/property-details/property-details.component';
import { PropertyFormComponent } from './components/property-form/property-form.component';
import { PropertyListComponent } from './components/property-list/property-list.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { SigninComponent } from './components/signin/signin.component';
import { WelcomeComponent } from './components/welcome/welcome.component';

const routes: Routes = [
    {path: '', component: WelcomeComponent},
    {path: 'welcome', component: WelcomeComponent},
    {path: 'property-list', component: PropertyListComponent},
    {path: 'property-form', component: PropertyFormComponent},
    {path: 'property-form/:id', component: PropertyFormComponent},
    {path: 'property-details/:id', component: PropertyDetailsComponent},
    {path: 'registration', component: RegistrationComponent},
    {path: 'signin', component: SigninComponent},
    {path: 'signin/:id', component: SigninComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
