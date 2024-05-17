import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {environment} from "../../environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isValid = new Subject<boolean>();
  private role = new Subject<string>();
  role$: Observable<string> = this.role.asObservable();
  valid$: Observable<boolean> = this.isValid.asObservable();
  private baseUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) { }

  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/register`, user);
  }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/login`, credentials);
  }

  saveToken(token: string): void {
    localStorage.setItem('auth_token', token);
  }

  registerUser(user: any):Observable<any>{
    return this.http.post(`${this.baseUrl}/auth/registerUser`, user);
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  isTokenValid(username: string):Observable<any>{
    const token = this.getToken();
    return this.http.post(`${this.baseUrl}/auth/valid`,username, {headers:{ Authorization: `Bearer ${token}` }});
  }

  setIsValid(isValid: boolean) {
    this.isValid.next(isValid);
  }

  setRole(role: string){
    this.role.next(role)
  }
}
