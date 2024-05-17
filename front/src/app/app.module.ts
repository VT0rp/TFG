import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { InicioComponent } from './components/inicio/inicio.component';
import { UserManageComponent } from './components/user-manage/user-manage.component';
import { FacturaComponent } from './components/factura/factura.component';
import { FacturaManageComponent } from './components/factura-manage/factura-manage.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { NavComponent } from './components/nav/nav.component';
import { FooterComponent } from './components/footer/footer.component';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { UserUpdateComponent } from './components/user-update/user-update.component';
import { FacturaCreateComponent } from './components/factura-create/factura-create.component';
import { FacturaGenerateComponent } from './components/factura-generate/factura-generate.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    InicioComponent,
    UserManageComponent,
    FacturaComponent,
    FacturaManageComponent,
    NavComponent,
    FooterComponent,
    RegisterUserComponent,
    UserUpdateComponent,
    FacturaCreateComponent,
    FacturaGenerateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    FontAwesomeModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
