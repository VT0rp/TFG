import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../services/user.service";
import {User} from "../../interfaces/UserPage";
import {GenerateService} from "../../services/generate.service";

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit{
  user?: User;
  username: string = '';
  valid?: boolean;
  role: string = '';
  printing: boolean = false;
  hidden: string = '';

  constructor(private authService: AuthService, private router: Router, private http: HttpClient, private userService: UserService, private generateService: GenerateService) {
  }

  ngOnInit() {
    this.generateService.printing$.subscribe(printing => {
      this.printing = printing;
      if(this.printing){
        this.hidden = 'hidden';
      }else{
        this.hidden = '';
      }
    })
    if(this.tokenExists()){
      this.userService.getUser().subscribe({
        next: (value) => {
          this.username = value.username;
          this.user = value;
          this.role = value.role;
          this.authService.setRole(value.role);
          localStorage.setItem("role", value.role);
          localStorage.setItem("email", value.email);
          localStorage.setItem("userId", value.id);
          this.authService.isTokenValid(this.username).subscribe({
            next: value => {
              this.valid = value;
              this.authService.setIsValid(value);
            }
          })
        },
        error: (error) => {
          console.error('Error fetching username', error);
        }
      });
    }
  }

  toInicio(){
    this.router.navigateByUrl("/inicio").then(()=>{
      window.location.reload();
    })
  }

  tokenExists(){
    return this.authService.getToken() != null;
  }

  logIn(){
    this.router.navigateByUrl("/login");
  }
  logOut(){
    localStorage.removeItem('auth_token');
    localStorage.removeItem('email');
    localStorage.removeItem('userId');
    localStorage.removeItem('role');
    this.authService.setIsValid(false);
    this.authService.setRole('');
    this.router.navigateByUrl("/inicio").then(() => window.location.reload());
  }

}
