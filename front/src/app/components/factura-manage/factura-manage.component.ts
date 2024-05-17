import {Component, OnInit} from '@angular/core';
import {faFilter, faSquarePen, faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {FacturaService} from "../../services/factura.service";
import {Factura} from "../../interfaces/FacturaPage";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-factura-manage',
  templateUrl: './factura-manage.component.html',
  styleUrls: ['./factura-manage.component.css']
})
export class FacturaManageComponent implements OnInit{
  role?: string | null = localStorage.getItem("role");
  userId?: string | null = localStorage.getItem("userId");
  listaFacturas: Factura[] = [];
  nombre: string = '';
  page:number = 0;
  size:number = 5;
  totalPages: number = 0;

  constructor(private facturaService: FacturaService, private router: Router) {
  }

  ngOnInit() {
    this.getAction();
  }

  filtered(){
    this.getAction();
  }

  deleteUser(id: string){
    this.facturaService.deleteFactura(id).subscribe({
      error: error => {
        console.error(error);
      },
      complete: () => {
        console.log("Persona con id: " + id + " eliminada correctamente");
        this.page = 0;
        this.getAction();
      }
    })
  }

  anterior(){
    if(this.page != 0){
      this.page = this.page - 1;
      this.getAction();
    }
  }

  siguiente(){
    if(this.page != (this.totalPages - 1)){
      this.page = this.page + 1;
      this.getAction();
    }
  }

  private getAllFacturasPagedFilteredByUserId(): void {
    let userId: string | null = localStorage.getItem("userId");
    if(userId != null){
      this.facturaService.getPageByUserId(this.page, this.size,userId, this.nombre).subscribe({
        next: (data: any) => {
          this.listaFacturas = data.content;
          this.totalPages = data.totalPages;
          console.log(this.totalPages);
        },
        error: (error) => { console.error(error) }
      });
    }
  }

  private getAllFacturasPagedFilteredByEmail(): void {
    let email: string | null = localStorage.getItem("email");
    if(email != null){
      this.facturaService.getPageByEmail(this.page, this.size,email, this.nombre).subscribe({
        next: (data: any) => {
          this.listaFacturas = data.content;
          this.totalPages = data.totalPages;
          console.log(this.totalPages);
        },
        error: (error) => { console.error(error) }
      });
    }
  }

  generateFactura(id: string){
    this.router.navigateByUrl("/facturaGenerate/" + id);
  }

  reset(){
    this.nombre= '';
  }

  limpiarFiltrado(){
    this.reset();
    this.getAction()
  }

  getAction(){
    if(this.role == "USER"){
      this.getAllFacturasPagedFilteredByUserId();
    }
    if(this.role == "ADMIN"){
      this.getAllFacturasPagedFilteredByEmail();
    }
  }

  protected readonly faSquarePen = faSquarePen;
  protected readonly faTrashCan = faTrashCan;
  protected readonly faFilter = faFilter;
}
