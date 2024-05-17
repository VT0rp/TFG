import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {Observable} from "rxjs";
import {User, UserPage} from "../interfaces/UserPage";
import {environment} from "../../environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl: string = environment.apiUrl + '/api/v1/user'

  constructor(private http: HttpClient, private authService: AuthService) { }

  getRole(username: string):Observable<any>{
    const token = this.authService.getToken();
    return this.http.get<any>(this.baseUrl + "/role", {headers: { Authorization: `Bearer ${token}` }});
  }

  getUsername():Observable<any>{
    const token = this.authService.getToken();
    return this.http.get<any>(this.baseUrl + "/username", {headers: { Authorization: `Bearer ${token}` }})
  }

  getAllUsersFilter(page:number, size:number, email: string, username?:string):Observable<UserPage[]>{
    const token = this.authService.getToken();
    let urlEndpoint: string = this.baseUrl + "/filtered" + "?page=" + page + "&size=" + size + "&email=" + email + "&username=";
    if(username){
      urlEndpoint = urlEndpoint + username;
    }
    return this.http.get<UserPage[]>(urlEndpoint, {headers: { Authorization: `Bearer ${token}` }});
  }

  deleteUser(id: string){
    const token = this.authService.getToken();
    return this.http.delete(this.baseUrl + "/delete/" + id, {headers: { Authorization: `Bearer ${token}` }});
  }

  getUser():Observable<User>{
    const token = this.authService.getToken();
    return this.http.get<User>(this.baseUrl + "/getUser", {headers: { Authorization: `Bearer ${token}` }})
  }

  getUserById(id: string):Observable<User>{
    const token = this.authService.getToken();
    return this.http.get<User>(this.baseUrl + "/getUserById/" + id, {headers: { Authorization: `Bearer ${token}` }})
  }

  updateUser(id: string, user: User):Observable<any>{
    const token = this.authService.getToken();
    return this.http.put(this.baseUrl + "/update/" + id, user, {headers: { Authorization: `Bearer ${token}` }})
  }


}
