import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {User} from "../../interfaces/UserPage";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.css']
})
export class UserUpdateComponent implements OnInit{
  email = localStorage.getItem("email");
  id?: string;

  user: User = {id: '', username: '', firstName: '', lastName: '', email: this.email != null ? this.email : '', password: '', role: ''};

  constructor(private router: Router, private userService: UserService, private ar: ActivatedRoute, private authService: AuthService) {
  }

  ngOnInit() {
    this.id = this.ar.snapshot.params["id"];
    if(this.id){
      this.loadUser(this.id);
    }
  }

  userAction(form: NgForm){
    this.update();
  }

  cancel(){
    if (confirm('¿Estás seguro de que quieres cancelar?')) {
      console.log('Cancelado');
      this.router.navigateByUrl('/userManagement');
    }
  }

  preventFormSubmit(event: KeyboardEvent) {
    if (event.key === "Enter") {
      event.preventDefault();
    }
  }
  update(): void {
    this.userService.updateUser(this.ar.snapshot.params["id"],this.user).subscribe({
      next: (response) => {
        this.authService.saveToken(response.token);
        this.router.navigate(['/userManagement']).then(()=>{
          window.location.reload();
        })
      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }

  loadUser(id: string){
    this.userService.getUserById(id).subscribe({
      next: value => {
        this.user = value;
      }
    })
  }
}
