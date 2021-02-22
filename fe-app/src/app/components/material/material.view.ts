import {Component, Input, OnInit} from '@angular/core';
import {Material} from "../../interfaces/material.interface";
import {AuthenticationService} from "../../services/auth/authentication.service";
import {User} from "../../interfaces/user/User";
import {UserService} from "../../services/user.service";
import {RoleAuthenticatorService} from "../../services/role-authenticator.service";
import {Role} from "../../interfaces/user/Role";

@Component({
  selector: 'material',
  templateUrl: './material.view.html',
  styleUrls: ['./material.view.scss']
})
export class MaterialView implements OnInit {

  @Input() material: Material;
  user: User;
  constructor(
    private roleAuthenticatorService: RoleAuthenticatorService,
    private userService: UserService) { }

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
  }

  hasAnyRole(roles: Role[]) {
    return this.roleAuthenticatorService.hasAnyRole(roles)
  }


}
