import { Component, OnInit } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faDashboard, faLocation, faShop, faBox, faMoneyBill, faChartBar, faContactBook, faHand, faBars, faTimes } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';
import { DataTimeComponent } from "../data-time/data-time.component"; // Correct import for Angular Router

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.scss'],
  standalone: true,
  imports: [FontAwesomeModule, DataTimeComponent]
})
export class SideNavComponent implements OnInit {
  faDashboard = faDashboard;
  faLocation = faLocation;
  faShop = faShop;
  faBox = faBox;
  faMoneyBill = faMoneyBill;
  faChartBar = faChartBar;
  faContactBook = faContactBook;
  faHand = faHand;
  faBars = faBars;
  faTimes = faTimes;

  isOpen = false; 

  constructor(private router: Router) {} 

  ngOnInit(): void {
  }
  
  toggleSidebar() {
    this.isOpen = !this.isOpen;
  }

  closeSidebar() {
    this.isOpen = false; 
  }

  navigateToLigne5() {
    this.router.navigate(['/ligne5']); 
    this.closeSidebar();
  }
}
