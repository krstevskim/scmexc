import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CourseListComponent} from "./course-list/course-list.component";
import {CoursePageComponent} from "./course-page/course-page.component";

const routes: Routes = [
  {
    path: 'courses',
    component: CourseListComponent
  },
  {
    path: 'courses/:id',
    component: CoursePageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
