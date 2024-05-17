import { Component } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials: any = {};
  errorMessage: string = '';

  constructor(private authService: AuthService,
              private router: Router
  ) { }

  login(): void {
    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        this.errorMessage = '';
        this.authService.saveToken(response.token);
        this.router.navigate(['/inicio']).then(() => window.location.reload());
      },
      error: (error) => {
        console.error('Login failed', error);
        this.errorMessage = 'Error en la autenticación. Por favor, revisa tus credenciales e intenta de nuevo.';
      }
    });
  }

  preventFormSubmit(event: KeyboardEvent) {
    if (event.key === "Enter") {
      event.preventDefault();
    }
  }

}
