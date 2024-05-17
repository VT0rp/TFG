import {Component, OnInit} from '@angular/core';
import {Factura, Item} from "../../interfaces/FacturaPage";
import {ActivatedRoute, Router} from "@angular/router";
import {FacturaService} from "../../services/factura.service";
import {GenerateService} from "../../services/generate.service";

@Component({
  selector: 'app-factura-generate',
  templateUrl: './factura-generate.component.html',
  styleUrls: ['./factura-generate.component.css']
})
export class FacturaGenerateComponent implements OnInit{
  id: string = this.ar.snapshot.params["id"];
  generating: boolean = false;
  itemsList: Item[] = [];
  email: string  | null = localStorage.getItem("email");
  userId: string  | null = localStorage.getItem("userId");

  factura: Factura = {id: '', nombre: '', email: '', userId: '', items: [], descuento: 0, total: 0, totalIva: 0};

  constructor(private facturaService: FacturaService, private ar: ActivatedRoute, private generateService: GenerateService) {
  }

  ngOnInit() {
    this.loadFactura();
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

  generateFactura(){
    this.generating = true;
    localStorage.setItem("generating", 'generating');
    this.generateService.setPrinting(true);
    // Espera 2 segundos (2000 milisegundos)
    setTimeout(() => {
      window.print();
    });
    setTimeout(() => {
      this.generateService.setPrinting(false);
      this.generating = false;
    }, 1000);
  }
}
