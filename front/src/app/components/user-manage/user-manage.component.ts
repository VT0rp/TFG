import { Component } from '@angular/core';
import {faFilter, faSquarePen, faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {UserService} from "../../services/user.service";
import {User} from "../../interfaces/UserPage";

@Component({
  selector: 'app-user-manage',
  templateUrl: './user-manage.component.html',
  styleUrls: ['./user-manage.component.css']
})
export class UserManageComponent {

  listaUsers: User[] = [];
  username: string = '';
  page:number = 0;
  size:number = 5;
  totalPages: number = 0;

  constructor(private userService: UserService) {
    this.getAllUsersPagedFiltered();
  }

  filtered(){
    this.getAllUsersPagedFiltered();
  }

  deleteUser(id: string){
    this.userService.deleteUser(id).subscribe({
      error: error => {
        console.error(error);
      },
      complete: () => {
        console.log("Persona con id: " + id + " eliminada correctamente");
        this.page = 0;
        this.getAllUsersPagedFiltered();
      }
    })
  }

  anterior(){
    if(this.page != 0){
      this.page = this.page - 1;
      this.getAllUsersPagedFiltered();
    }
  }

  siguiente(){
    if(this.page != (this.totalPages - 1)){
      this.page = this.page + 1;
      this.getAllUsersPagedFiltered();
    }
  }

  private getAllUsersPagedFiltered(): void {
    let email: string | null = localStorage.getItem("email");
    if(email != null){
      this.userService.getAllUsersFilter(this.page, this.size,email, this.username).subscribe({
        next: (data: any) => {
          this.listaUsers = data.content;
          this.totalPages = data.totalPages;
        },
        error: (error) => { console.error(error) }
      });
    }
  }

  reset(){
    this.username= '';
  }

  limpiarFiltrado(){
    this.reset();
    this.getAllUsersPagedFiltered();
  }

  protected readonly faSquarePen = faSquarePen;
  protected readonly faTrashCan = faTrashCan;
  protected readonly faFilter = faFilter;
}
