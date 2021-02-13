import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CourseListComponent} from "./components/course-list/course-list.component";
import {CoursePageComponent} from "./components/course-page/course-page.component";
import {LoginPageComponent} from "./components/login-page/login-page.component";
import {RegisterPageComponent} from "./components/register-page/register-page.component";
import {AuthGuard} from "./guards/auth.guard";

const routes: Routes = [
  {
    path:'login',
    component: LoginPageComponent
  },
  {
    path:'register',
    component: RegisterPageComponent
  },
  {
    path: 'courses',
    component: CourseListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'courses/:id',
    component: CoursePageComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '',
    redirectTo: 'courses',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: '',
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
