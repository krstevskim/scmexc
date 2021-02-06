import { Component, OnInit } from '@angular/core';
import {Course} from "../interfaces/course.interface";

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss']
})
export class CourseListComponent implements OnInit {

  data: Course[] = [
    {id:1, name: "Course 1", code:"C1", description: "Description Course 1", numMaterials:13},
    {id:2, name: "Course 2", code:"C2", description: "Description Course 2", numMaterials:14},
    {id:3, name: "Course 3", code:"C3", description: "Description Course 3", numMaterials:15},
    {id:4, name: "Course 4", code:"C4", description: "Description Course 4", numMaterials:16},
    {id:5, name: "Course 5", code:"C5", description: "Description Course 5", numMaterials:17},
  ]

  dataDisplayed: Course[];

  search: string = "";



  constructor() {
    this.dataDisplayed = this.data;
  }

  ngOnInit(): void {
  }

  searchInputChange(input:string) {
    this.dataDisplayed = this.data.filter(el => el.name.toLowerCase().includes(input.toLowerCase()) ||  el.code.toLowerCase().includes(input.toLowerCase()));
  }

}
