import {Component, Input, OnInit} from '@angular/core';
import {Course} from "../../../interfaces/course.interface";
import {CourseService} from "../../../services/course.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MaterialService} from "../../../services/material.service";
import {Observable} from "rxjs";
import {Material} from "../../../interfaces/material/material.interface";

@Component({
  selector: 'app-course-page',
  templateUrl: './course-page.component.html',
  styleUrls: ['./course-page.component.scss']
})
export class CoursePageComponent implements OnInit {

  course: Course;
  courseId: number;
  materials$: Observable<Material[]>
  materials: Material[];
  constructor(private service: CourseService,
              private router: Router,
              private route: ActivatedRoute,
              private materialService: MaterialService) {
    console.log("INIT")
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      console.log(params);
      this.courseId = +params['id'];
      this.service.getCourseById(this.courseId).subscribe(el => {
        this.course = el;
      })
      this.materials$ = this.materialService.getMaterialsByCourseId(this.courseId);
    });
  }

  get url() {
    return "/courses/create"+this.courseId
  }

}
