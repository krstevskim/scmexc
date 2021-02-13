import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/auth/authentication.service";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  loginForm: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthenticationService) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4)]]
    });
  }
  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {

    if (!this.loginForm.valid) {
      // this.notifierService.notify('error', NotifierMessages.ALL_FIELDS_REQUIRED);
      console.log("invalid form")
      return;
    }

    const loginData = this.loginForm.value;
    // this.notifierService.notify('info', 'Logging in, please wait...');
    this.authService.authenticate(loginData.email, loginData.password).subscribe(
      sucess => window.location.href = '/',
      error => {
        // this.notifierService.notify('error', 'Your error or username is invalid.');
        console.log(error)
      }
    );

  }
}
