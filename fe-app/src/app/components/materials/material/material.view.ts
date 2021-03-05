import {Component, Input, OnInit} from '@angular/core';
import {Material} from "../../../interfaces/material/material.interface";
import {AuthenticationService} from "../../../services/user-auth/authentication.service";
import {User} from "../../../interfaces/user/User";
import {UserService} from "../../../services/user-auth/user.service";
import {RoleAuthenticatorService} from "../../../services/user-auth/role-authenticator.service";
import {Role} from "../../../interfaces/user/Role";
import {Observable} from "rxjs";
import {MaterialService} from "../../../services/material.service";
import {Router} from "@angular/router";

@Component({
  selector: 'material',
  templateUrl: './material.view.html',
  styleUrls: ['./material.view.scss']
})
export class MaterialView implements OnInit {

  @Input() material: Material;
  @Input() canOpenFull: boolean;
  @Input() courseId: number;
  user: User;
  constructor(
    private roleAuthenticatorService: RoleAuthenticatorService,
    private userService: UserService,
    private service: MaterialService,
    private router: Router) { }

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
  }

  hasAnyRole(roles: Role[]) {
    return this.roleAuthenticatorService.hasAnyRole(roles)
  }

  get hasUnPublishPermission() {
    return this.hasAnyRole([Role.ROLE_ADMIN, Role.ROLE_SUPER_ADMIN, Role.ROLE_MODERATOR]);
  }


  unpublishMaterial() {

  }

  openFullPage() {
    if(this.canOpenFull) {
      this.router.navigate(['/courses', this.courseId, 'material', this.material.id]);
    }
  }
}
