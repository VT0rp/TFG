import {Component, OnInit} from '@angular/core';
import {GenerateService} from "../../services/generate.service";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit{
  printing: boolean = false;
  class: string = 'text-light';

  constructor(private generateService: GenerateService) {
  }

  ngOnInit() {
    this.generateService.printing$.subscribe(printing => {
      this.printing = printing;
      if(this.printing){
        this.class = 'text-dark';
      }else{
        this.class = 'text-light';
      }
    })
  }

}
