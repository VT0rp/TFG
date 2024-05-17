import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent implements OnInit{
  isValid: boolean = false;
  role: string = '';

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    this.authService.valid$.subscribe(isValid => {
      this.isValid = isValid;
    });
    this.authService.role$.subscribe(role => {
      this.role = role;
    });
  }

}
