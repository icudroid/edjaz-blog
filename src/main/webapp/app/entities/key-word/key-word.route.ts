import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { KeyWordComponent } from './key-word.component';
import { KeyWordDetailComponent } from './key-word-detail.component';
import { KeyWordPopupComponent } from './key-word-dialog.component';
import { KeyWordDeletePopupComponent } from './key-word-delete-dialog.component';

@Injectable()
export class KeyWordResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const keyWordRoute: Routes = [
    {
        path: 'key-word',
        component: KeyWordComponent,
        resolve: {
            'pagingParams': KeyWordResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.keyWord.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'key-word/:id',
        component: KeyWordDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.keyWord.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const keyWordPopupRoute: Routes = [
    {
        path: 'key-word-new',
        component: KeyWordPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.keyWord.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'key-word/:id/edit',
        component: KeyWordPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.keyWord.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'key-word/:id/delete',
        component: KeyWordDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.keyWord.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
