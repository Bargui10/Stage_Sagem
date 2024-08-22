import { Component, Input, OnInit, ElementRef } from '@angular/core';
import * as Highcharts from 'highcharts';

@Component({
  selector: 'app-highcharts-wrapper',
  standalone: true,
  template: `<div></div>`
})
export class HighchartsWrapperComponent implements OnInit {
  @Input() Highcharts: typeof Highcharts | undefined;
  @Input()
    options!: Highcharts.Options;

  constructor(private el: ElementRef) {}

  ngOnInit() {
    Highcharts.chart(this.el.nativeElement, this.options);
  }
}
