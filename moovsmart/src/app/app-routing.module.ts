import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminPropertyDetailsComponent } from './components/admin-property-details/admin-property-details.component';
import { AdminComponent } from './components/admin/admin.component';
import { DeletePictureComponent } from './components/delete-picture/delete-picture.component';
import { ProfilListComponent } from './components/profil-list/profil-list.component';
import { PropertyDetailsComponent } from './components/property-details/property-details.component';
import { PropertyFormComponent } from './components/property-form/property-form.component';
import { PropertyListComponent } from './components/property-list/property-list.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { SigninComponent } from './components/signin/signin.component';
import { WelcomeComponent } from './components/welcome/welcome.component';

const routes: Routes = [
    {path: '', component: WelcomeComponent},
    {path: 'welcome', component: WelcomeComponent},
    {path: 'property-details/:id/images', component: DeletePictureComponent},
    {path: 'property-list', component: PropertyListComponent},
    {path: 'property-list/:id', component: PropertyListComponent},
    {path: 'property-form', component: PropertyFormComponent},
    {path: 'property-form/:id', component: PropertyFormComponent},
    {path: 'property-details/:id', component: PropertyDetailsComponent},
    {path: 'registration', component: RegistrationComponent},
    {path: 'signin', component: SigninComponent},
    {path: 'signin/:id', component: SigninComponent},
    {path: 'profil-list', component: ProfilListComponent},
    {path: 'profil-list/:id', component: ProfilListComponent},
    {path: 'admin', component: AdminComponent},
    {path: 'admin/details/:id', component: AdminPropertyDetailsComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(routes,{useHash:true})],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
