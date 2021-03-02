import { BrowserModule } from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MatIconModule} from "@angular/material/icon";
import {MatMenuModule} from '@angular/material/menu';
import { MatCardModule} from "@angular/material/card";

import { CourseListComponent } from './components/course-list/course-list.component';
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatGridListModule} from "@angular/material/grid-list";
import {MAT_DATE_LOCALE, MatRippleModule} from "@angular/material/core";
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { CoursePageComponent } from './components/course-page/course-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegisterPageComponent } from './components/register-page/register-page.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {AuthenticationService} from "./services/auth/authentication.service";
import {UserService} from "./services/user.service";
import {UserStateService} from "./services/user-state.service";
import {BasicAuthHttpInterceptor} from "./services/auth/basic-auth-http.interceptor";
import { MenuComponent } from './components/menu/menu.component';
import { CommentWrapperView } from './components/comment-wrapper/comment-wrapper.view';
import { CommentView } from './components/comment/comment.view';
import { MaterialView } from './components/material/material.view';
import {MatExpansionModule} from '@angular/material/expansion';
import {TruncatePipe} from "./interfaces/truncate-pipe";
import { ItemWrapperView } from './components/item-wrapper/item-wrapper.view';
import { MaterialCreateComponent } from './components/material-create/material-create.component';
import { ItemCreateWrapperComponent } from './components/item-create-wrapper/item-create-wrapper.component';
import { CourseCreateComponent } from './components/course-create/course-create.component';
import {NotifierModule, NotifierOptions} from "angular-notifier";
import { MaterialListComponent } from './components/admin-pages/material-list/material-list.component';
import { UserListComponent } from './components/admin-pages/user-list/user-list.component';
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {MatTableModule} from "@angular/material/table";
import {MatSelectModule} from "@angular/material/select";
import {NgxMatFileInputModule} from "@angular-material-components/file-input";
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { MaterialPageComponent } from './components/material-page/material-page.component';
import { UserComponent } from './components/user/user.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';

const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: 'right',
      distance: 12
    },
    vertical: {
      position: 'top',
      distance: 12,
      gap: 10
    }
  },
  theme: 'material',
  behaviour: {
    autoHide: 5000,
    onClick: 'hide',
    onMouseover: 'pauseAutoHide',
    showDismissButton: true,
    stacking: 4
  },
  animations: {
    enabled: true,
    show: {
      preset: 'slide',
      speed: 300,
      easing: 'ease'
    },
    hide: {
      preset: 'fade',
      speed: 300,
      easing: 'ease',
      offset: 50
    },
    shift: {
      speed: 300,
      easing: 'ease'
    },
    overlap: 150
  }
};

@NgModule({
  declarations: [
    AppComponent,
    CourseListComponent,
    CoursePageComponent,
    LoginPageComponent,
    RegisterPageComponent,
    MenuComponent,
    CommentWrapperView,
    CommentView,
    MaterialView,
    TruncatePipe,
    ItemWrapperView,
    MaterialCreateComponent,
    ItemCreateWrapperComponent,
    CourseCreateComponent,
    MaterialListComponent,
    UserListComponent,
    MaterialPageComponent,
    UserComponent,
    ChangePasswordComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatIconModule,
        MatMenuModule,
        MatCardModule,
        MatButtonModule,
        MatFormFieldModule,
        MatGridListModule,
        MatRippleModule,
        MatInputModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        MatExpansionModule,
        MatPaginatorModule,
        MatSortModule,
        MatTableModule,
        NotifierModule.withConfig(customNotifierOptions),
        MatSelectModule,
        NgxMatFileInputModule,
        MatProgressBarModule
    ],
  providers: [AuthenticationService,
    UserService,
    {
      provide: APP_INITIALIZER,
      useFactory: (us: UserService) => () => us.load(),
      deps: [UserService],
      multi: true
    },
    UserStateService,
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthHttpInterceptor, multi: true },
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB'}],
  bootstrap: [AppComponent]
})
export class AppModule { }
