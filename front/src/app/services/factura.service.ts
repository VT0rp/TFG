import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {UserPage} from "../interfaces/UserPage";
import {Factura, FacturaPage} from "../interfaces/FacturaPage";
import {Observable} from "rxjs";
import {FacturaCreate} from "../interfaces/FacturaCreate";

@Injectable({
  providedIn: 'root'
})
export class FacturaService {
  private baseUrl: string = 'http://localhost:8080/api/v1/factura'

  constructor(private http: HttpClient, private authService: AuthService) { }

  getPageByUserId(page: number, size: number, userId: string, nombre: string):Observable<FacturaPage>{
    const token = this.authService.getToken();
    let urlEndpoint: string = this.baseUrl + "/pagedUserId/" + userId + "?page=" + page + "&size=" + size + "&nombre=";
    if(nombre){
      urlEndpoint = urlEndpoint + nombre;
    }
    return this.http.get<FacturaPage>(urlEndpoint, {headers: { Authorization: `Bearer ${token}` }});
  }

  getPageByEmail(page: number, size: number, email: string, nombre: string):Observable<FacturaPage>{
    const token = this.authService.getToken();
    let urlEndpoint: string = this.baseUrl + "/pagedEmail" + "?page=" + page + "&size=" + size + "&email=" + email + "&nombre=";
    if(nombre){
      urlEndpoint = urlEndpoint + nombre;
    }
    return this.http.get<FacturaPage>(urlEndpoint, {headers: { Authorization: `Bearer ${token}` }});
  }

  deleteFactura(id:string){
    const token = this.authService.getToken();
    return this.http.delete(this.baseUrl + "/delete/" + id, {headers: { Authorization: `Bearer ${token}` }});
  }

  createFactura(factura:FacturaCreate){
    const token = this.authService.getToken();
    return this.http.post(this.baseUrl + "/create", factura, {headers: { Authorization: `Bearer ${token}` }})
  }

  updateFactura(id: string, factura: Factura):Observable<any>{
    const token = this.authService.getToken();
    return this.http.put(this.baseUrl + "/update/" + id, factura, {headers: { Authorization: `Bearer ${token}` }});
  }

  getById(id: string):Observable<Factura>{
    const token = this.authService.getToken();
    return this.http.get<Factura>(this.baseUrl + "/get/" + id, {headers: { Authorization: `Bearer ${token}` }})
  }
}
