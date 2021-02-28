import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../interfaces/user/User";
import {NavItem} from "../../interfaces/nav-item.interface";
import {Role} from "../../interfaces/user/Role";
import {RoleAuthenticatorService} from "../../services/role-authenticator.service";
import {AuthenticationService} from "../../services/auth/authentication.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  @Input() user: User;

  navItems: NavItem[] = [
    {
      name: 'Home',
      icon: 'home',
      route: '/',
      canAccess: true
    },
    {
      name: 'Account',
      icon: 'supervisor_account',
      route: '/edit-account',
      canAccess: this.isAuthenticated()
    },
    {
      name: 'Courses',
      icon: 'book',
      route: '/courses',
      canAccess: true
    },
    {
      name: 'Admin Pages',
      icon: 'admin',
      route: '/admin',
      canAccess: this.hasAnyRole([Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN]),
      children: [{
        name: 'Users',
        icon: 'managed_accounts',
        route: '/admin/users',
        canAccess: this.hasAnyRole([Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN]),
      }]
    }
  ]

  constructor(
    private roleAuthenticatorService: RoleAuthenticatorService,
    private authService: AuthenticationService
  ) {
  }

  ngOnInit(): void {
  }

  hasRole(role: Role) {
    return true;
    // return this.roleAuthenticatorService.hasRole(role);
  }

  hasAnyRole(roles: Role[]) {
    return true;
    // return this.roleAuthenticatorService.hasAnyRole(roles)
  }

  isAuthenticated() {
    return this.authService.isUserLoggedIn();
  }

  logout() {
    this.authService.logOut();
  }

}
