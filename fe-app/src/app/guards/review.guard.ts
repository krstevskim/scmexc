import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable, of} from 'rxjs';
import {UserService} from '../services/user.service';
import {flatMap} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class ReviewGuard implements CanActivate {
    constructor(private userService: UserService) {
    }

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        // let currentUser = this.userService.getCurrentUser();
        return true;
        // return review$.pipe(
        //     flatMap(rev => {
        //         if (currentUser.role.name == RoleName.ROLE_ADMINISTRATOR) {
        //             return of(true);
        //         } else if (rev.assignedFor.id == currentUser.id) {
        //             return of(true);
        //         } else if (currentUser.id == rev.assignedFor.mentor?.id) {
        //             return of(true);
        //         } else {
        //             return this.teamService.isTeamLeaderOf(rev.assignedFor.id);
        //         }
        //     })
        // );
    }

}
