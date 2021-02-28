import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Item} from "../../interfaces/item.interface";
import {ItemType} from "../../interfaces/item-type.enum";

@Component({
  selector: 'item-wrapper',
  templateUrl: './item-wrapper.view.html',
  styleUrls: ['./item-wrapper.view.scss']
})
export class ItemWrapperView  {

  @Input() items: Item[];
  itemTypes = ItemType;
  constructor() { }

  downloadFile(item: Item) {
    window.open(item.url);
  }

}
