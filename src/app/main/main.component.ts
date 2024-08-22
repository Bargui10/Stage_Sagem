import { Component } from '@angular/core';
import { TopWidgetsComponent } from '../top-widgets/top-widgets.component';
import { DataTimeComponent } from '../data-time/data-time.component';
import { Top3Component } from '../Top3/Top3.component';
import { PerfByShiftComponent } from '../PerfByShift/PerfByShift.component';
import { HeaderComponent } from "../header/header.component";

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    TopWidgetsComponent,
    Top3Component,
    PerfByShiftComponent,
    DataTimeComponent,
    HeaderComponent
],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {

}
