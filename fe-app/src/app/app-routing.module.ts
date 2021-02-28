import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CourseListComponent} from "./components/course-list/course-list.component";
import {CoursePageComponent} from "./components/course-page/course-page.component";
import {LoginPageComponent} from "./components/login-page/login-page.component";
import {RegisterPageComponent} from "./components/register-page/register-page.component";
import {AuthGuard} from "./guards/auth.guard";
import {CourseCreateComponent} from "./components/course-create/course-create.component";
import {AdminGuard} from "./guards/admin.guard";
import {UserListComponent} from "./components/admin-pages/user-list/user-list.component";
import {MaterialListComponent} from "./components/admin-pages/material-list/material-list.component";
import {MaterialCreateComponent} from "./components/material-create/material-create.component";
import {MaterialPageComponent} from "./components/material-page/material-page.component";
import {MaterialEditGuard} from "./guards/material-edit.guard";

const routes: Routes = [
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'register',
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
        children: [
          {
            path: 'material',
            children: [
              {
                path: 'create',
                component: MaterialCreateComponent
              },
              {
                path: 'create/:matId',
                component: MaterialCreateComponent,
                canActivate: [MaterialEditGuard]
              },
              {
                path: ':matId',
                //TODO ADD PAGE
                component: MaterialPageComponent
              },

            ]
          },
          {
            path: '',
            component: CoursePageComponent,
          }
        ]
      },
      {
        path: '',
        component: CourseListComponent,
      },
      {
        path: 'material',
        children: [
          {
            path: ':id',
            //TODO ADD PAGE
            component: MaterialCreateComponent
          },
          {
            path: 'create/:id',
            component: MaterialCreateComponent
          }
        ]
      }
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
export class AppRoutingModule {
}
