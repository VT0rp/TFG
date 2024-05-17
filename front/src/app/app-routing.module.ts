import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {InicioComponent} from "./components/inicio/inicio.component";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {UserManageComponent} from "./components/user-manage/user-manage.component";
import {RegisterUserComponent} from "./components/register-user/register-user.component";
import {UserUpdateComponent} from "./components/user-update/user-update.component";
import {FacturaComponent} from "./components/factura/factura.component";
import {FacturaManageComponent} from "./components/factura-manage/factura-manage.component";
import {FacturaCreateComponent} from "./components/factura-create/factura-create.component";
import {FacturaGenerateComponent} from "./components/factura-generate/factura-generate.component";

const routes: Routes = [
  {
    path: "",
    redirectTo: "inicio",
    pathMatch: "full"
  },
  {
    path: "inicio",
    component: InicioComponent
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  {
    path: "userManagement",
    component: UserManageComponent
  },
  {
    path:'userRegister',
    component: RegisterUserComponent
  },
  {
    path: 'userUpdate/:id',
    component: UserUpdateComponent
  },
  {
    path: 'facturaManagement',
    component: FacturaManageComponent
  },
  {
    path: 'factura/:id',
    component: FacturaComponent
  },
  {
    path: 'facturaCreate',
    component: FacturaCreateComponent
  },
  {
    path: 'facturaGenerate/:id',
    component: FacturaGenerateComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
