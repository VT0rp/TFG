import {Component, OnInit} from '@angular/core';
import {Factura, Item} from "../../interfaces/FacturaPage";
import {FacturaCreate} from "../../interfaces/FacturaCreate";
import {ActivatedRoute, Router} from "@angular/router";
import {FacturaService} from "../../services/factura.service";
import {NgForm} from "@angular/forms";
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-factura',
  templateUrl: './factura.component.html',
  styleUrls: ['./factura.component.css']
})
export class FacturaComponent implements OnInit{
  id: string = this.ar.snapshot.params["id"];
  itemsList: Item[] = [];
  item: Item = {nombre: '', cantidad: 0, precio: 0, descuento: 0, iva: 0};
  email: string  | null = localStorage.getItem("email");
  userId: string  | null = localStorage.getItem("userId");

  factura: Factura = {id: '', nombre: '', email: '', userId: '', items: [], descuento: 0, total: 0, totalIva: 0};

  constructor(private router: Router, private facturaService: FacturaService, private ar: ActivatedRoute) {
  }

  ngOnInit() {
    this.loadFactura();
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

  loadFactura(){
    if(this.id){
      this.facturaService.getById(this.id).subscribe({
        next: value => {
          this.factura = value;
          this.itemsList = this.factura.items;
        }
      })
    }
  }

  deleteItem(item: Item){
    const index = this.itemsList.indexOf(item);
    this.itemsList.splice(index, 1);
  }

  preventFormSubmit(event: KeyboardEvent) {
    if (event.key === "Enter") {
      event.preventDefault();
    }
  }

  getTotals(){
    this.getTotal();
    this.getTotalIva();
  }

  update(form: NgForm): void {
    if(this.userId != null){
      this.factura.userId = this.userId;
    }
    if(this.email != null){
      this.factura.email = this.email;
    }
    if(this.itemsList.length < 1){
      alert("Debes insertar algún item para crear la factura");
    }else{
      if(this.id){
        this.factura.items = this.itemsList;
        this.facturaService.updateFactura(this.id, this.factura).subscribe({
          next: (response) => {
            this.router.navigate(['/facturaManagement']).then(()=>{
              window.location.reload();
            })
          },
          error: (error) => {
            console.error('Error al actualizar la factura', error);
            alert('No se ha podido actualizar, es posible que el nombre exista');
          }
        });
      }
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
