import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PropertyListComponent} from "./components/property-list/property-list.component";
import {PropertyFormComponent} from "./components/property-form/property-form.component";
import {PropertyDetailsComponent} from "./components/property-details/property-details.component";
import { RegistrationComponent } from './components/registration/registration.component';
import { WelcomeComponent } from './components/welcome/welcome.component';

const routes: Routes = [
  {path: "", component: WelcomeComponent},
  {path: "welcome/:id", component: WelcomeComponent},
  {path: "property-list", component: PropertyListComponent},
  {path: "property-form", component: PropertyFormComponent},
  {path: 'property-form/:id', component: PropertyFormComponent},
  {path: "property-details/:id", component: PropertyDetailsComponent},
  {path: "registration", component: RegistrationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
