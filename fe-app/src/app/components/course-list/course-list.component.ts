import { Component, OnInit } from '@angular/core';
import {Course} from "../../interfaces/course.interface";
import {Observable} from "rxjs";
import {CourseService} from "../../services/course.service";

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss']
})
export class CourseListComponent implements OnInit {

  $courses: Observable<Course[]>;
  courses: Course[] = [];
  displayedCourses: Course[];
  search: string = '';
  constructor(private _service: CourseService) {  }

  ngOnInit(): void {
    this.$courses = this._service.getAllCourses();
    this.$courses.subscribe(el => {
      this.courses = el;
      console.log(el);
      this.displayedCourses =this.courses;
    });
  }

  searchInputChange(input:string) {
    // this.dataDisplayed = this.data.filter(el => el.name.toLowerCase().includes(input.toLowerCase()) ||  el.code.toLowerCase().includes(input.toLowerCase()));
    this.displayedCourses = this.courses.filter(el => el.name.toLowerCase().includes(input.toLowerCase()) ||  el.code.toLowerCase().includes(input.toLowerCase()));
  }

}
