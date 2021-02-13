import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatAccordion} from "@angular/material/expansion";
import {Item} from "../../interfaces/item.interface";

@Component({
  selector: 'item-wrapper',
  templateUrl: './item-wrapper.view.html',
  styleUrls: ['./item-wrapper.view.scss']
})
export class ItemWrapperView  {
  @ViewChild(MatAccordion) accordion: MatAccordion;

  @Input() items: Item[];
  constructor() { }

}
