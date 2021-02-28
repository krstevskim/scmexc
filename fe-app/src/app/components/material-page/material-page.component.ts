import { Component, OnInit } from '@angular/core';
import {User} from "../../interfaces/user/User";
import {RoleAuthenticatorService} from "../../services/role-authenticator.service";
import {UserService} from "../../services/user.service";
import {Role} from "../../interfaces/user/Role";
import {ActivatedRoute, Router} from "@angular/router";
import {Material} from "../../interfaces/material.interface";
import {MaterialService} from "../../services/material.service";

@Component({
  selector: 'app-material-page',
  templateUrl: './material-page.component.html',
  styleUrls: ['./material-page.component.scss']
})
export class MaterialPageComponent implements OnInit {

  user: User;
  materialId: number;
  material: Material;
  constructor(
    private roleAuthenticatorService: RoleAuthenticatorService,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private service: MaterialService) { }

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
    this.route.params.subscribe(params => {
      if (params['matId']) {
        this.materialId = +params['matId'];
        this.service.getMaterialById(this.materialId).subscribe(el => {
          this.material = el;
        });
      }
    });
  }

  hasAnyRole(roles: Role[]) {
    return this.roleAuthenticatorService.hasAnyRole(roles)
  }

}
