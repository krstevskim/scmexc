import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../../services/auth/authentication.service";
import {NotifierService} from "angular-notifier";
import {UserService} from "../../services/user.service";
import {User} from "../../interfaces/user/User";
import {RoleAuthenticatorService} from "../../services/role-authenticator.service";
import {Role} from "../../interfaces/user/Role";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthenticationService,
    private notifierService: NotifierService,
    private userService: UserService,
    private route: ActivatedRoute,
    private roleService: RoleAuthenticatorService
    ) { }
  currentUser: User;
  userId: number;
  hidePassword: boolean = true;
  userToUpdate: User;
  roles = Role;
  selectedRole: Role;
  hasRoleChangePermission:boolean = false;

  userForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required]],
      role: ['', Validators.required]
    }
  );

  ngOnInit(): void {
    this.currentUser = this.userService.getCurrentUser();
    if(this.roleService.hasRole(Role.ROLE_SUPER_ADMIN)) {
      this.route.params.subscribe(params => {
        if (params['id']) {
          this.userId = +params['id'];
          this.userService.getUserById(this.userId).subscribe(el => {
            this.userToUpdate = el;
            console.log(this.userToUpdate);
            this.userForm.patchValue(this.userToUpdate);
            this.selectedRole = el.role;
            this.hasRoleChangePermission = true;
          })
        }
      });
    } else {
      this.userForm.patchValue(this.currentUser);
    }
  }



  get f() {
    return this.userForm.controls;
  }

  onSubmit() {
    let newUser = this.userForm.value;
    console.log(newUser)
    this.authService.changeDetails(newUser).subscribe(
      success => {
        this.notifierService.notify('success', 'Successfully updated user details');
      },
      error => {
        console.error(error);
        this.notifierService.notify('error', 'Error updating user.');
      }
    );
  }
}
