import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {Factura, Item} from "../../interfaces/FacturaPage";
import {FacturaCreate} from "../../interfaces/FacturaCreate";
import {FacturaService} from "../../services/factura.service";
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-factura-create',
  templateUrl: './factura-create.component.html',
  styleUrls: ['./factura-create.component.css']
})
export class FacturaCreateComponent implements  OnInit{
  itemsList: Item[] = [];
  item: Item = {nombre: '', cantidad: 0, precio: 0, descuento: 0, iva: 0};
  email: string  | null = localStorage.getItem("email");
  userId: string  | null = localStorage.getItem("userId");

  factura: FacturaCreate = {nombre: '', email: '', userId: '', items: [], descuento: 0, total: 0, totalIva: 0};

  constructor(private router: Router, private facturaService: FacturaService) {
  }

  ngOnInit() {
  }

  preventFormSubmit(event: KeyboardEvent) {
    if (event.key === "Enter") {
      event.preventDefault();
    }
  }

  addItem(form: NgForm){
    this.itemsList.push(this.item);
    this.resetItemForm();
    this.getTotals();
  }

  cancel(){
    if (confirm('¿Estás seguro de que quieres cancelar?')) {
      console.log('Cancelado');
      this.router.navigateByUrl('/facturaManagement');
    }
  }

  getTotals(){
    this.getTotal();
    this.getTotalIva();
  }

  deleteItem(item: Item){
    const index = this.itemsList.indexOf(item);
    this.itemsList.splice(index, 1);
  }

  create(form: NgForm): void {
    if(this.userId != null){
      this.factura.userId = this.userId;
    }
    if(this.email != null){
      this.factura.email = this.email;
    }
    if(this.itemsList.length < 1){
      alert("Debes insertar algún item para crear la factura");
    }else{
      this.factura.items = this.itemsList;
      this.facturaService.createFactura(this.factura).subscribe({
        next: (response) => {
          this.router.navigate(['/facturaManagement']).then(()=>{
            window.location.reload();
          })
        },
        error: (error) => {
          if(!error){
          }else{
            console.error('There was an error!', error);
            alert("No se ha podido realizar la acción, es posible que el nombre exista");
          }
        }
      });
    }
  }

  getTotal(){
    let total = 0;
    let totalSinDescuento: number = 0;
    let totalConDescuento: number = 0;
    for (let item of this.itemsList){
      totalSinDescuento = item.cantidad * item.precio;
      totalConDescuento = totalSinDescuento - (totalSinDescuento * (item.descuento / 100));
      total += totalConDescuento;
    }
    total = total * (1 - this.factura.descuento / 100);
    this.factura.total = total;
  }

  getTotalIva(){
    let total = 0;
    let totalSinDescuento: number = 0;
    let totalConDescuento: number = 0;
    let totalConIva: number = 0;
    for (let item of this.itemsList){
      totalSinDescuento = item.cantidad * item.precio;
      totalConDescuento = totalSinDescuento - (totalSinDescuento * (item.descuento / 100));
      totalConIva = totalConDescuento + (totalConDescuento * (item.iva / 100));
      total += totalConIva;
    }
    total = total * (1 - this.factura.descuento / 100);
    this.factura.totalIva = total;
  }


  resetItemForm(){
    this.item = {nombre: '', cantidad: 0, precio: 0, descuento: 0, iva: 0};
  }

    protected readonly faTrashCan = faTrashCan;
}
