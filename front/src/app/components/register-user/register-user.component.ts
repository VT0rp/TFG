import {Component, OnInit} from '@angular/core';
import {UserRegister} from "../../interfaces/UserRegister";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit{
  email = localStorage.getItem("email");

  user: UserRegister = {username: '', firstName: '', lastName: '', email: this.email != null ? this.email : '', password: '', role: 'USER'};

  constructor(private router: Router, private authService: AuthService) {
  }

  ngOnInit() {
  }

  userAction(form: NgForm){
    this.register();
  }

  preventFormSubmit(event: KeyboardEvent) {
    if (event.key === "Enter") {
      event.preventDefault();
    }
  }

  cancel(){
    if (confirm('¿Estás seguro de que quieres cancelar?')) {
      console.log('Cancelado');
      this.router.navigateByUrl('/userManagement');
    }
  }

  register(): void {
    this.authService.registerUser(this.user).subscribe({
      next: (response) => {
        this.router.navigate(['/userManagement']).then(()=>{
          window.location.reload();
        })
      },
      error: (error) => {
        console.error('Error al registrar el usuario', error);
        alert("El usuario ya existe");
      }
    });
  }
}
