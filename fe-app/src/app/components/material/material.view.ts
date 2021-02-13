import {Component, Input, OnInit} from '@angular/core';
import {Material} from "../../interfaces/material.interface";

@Component({
  selector: 'material',
  templateUrl: './material.view.html',
  styleUrls: ['./material.view.scss']
})
export class MaterialView implements OnInit {

  @Input() material: Material;
  constructor() { }

  ngOnInit(): void {
  }

}
