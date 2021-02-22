import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CourseListComponent} from "./components/course-list/course-list.component";
import {CoursePageComponent} from "./components/course-page/course-page.component";
import {LoginPageComponent} from "./components/login-page/login-page.component";
import {RegisterPageComponent} from "./components/register-page/register-page.component";
import {AuthGuard} from "./guards/auth.guard";
import {CourseCreateComponent} from "./components/course-create/course-create.component";
import {AdminGuard} from "./guards/admin.guard";
import {UserListComponent} from "./components/admin-pages/user-list/user-list.component";
import {MaterialListComponent} from "./components/admin-pages/material-list/material-list.component";

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
    children: [
      {
        path: 'create/:id',
        component: CourseCreateComponent //admin guard
      },
      {
        path: 'create',
        component: CourseCreateComponent
      },
      {
        path: ':id',
        component: CoursePageComponent
      },
      {
        path: '',
        component: CourseListComponent,
      },
    ],
    canActivate: [AuthGuard]
  },
  {
    path: 'admin',
    children: [
      {
        path: 'users',
        component: UserListComponent
      },
      {
        path: 'materials',
        component: MaterialListComponent
      }
    ],
    canActivate: [AuthGuard, AdminGuard] //adminguard
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
