import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BlogItemComponent } from './blog-item.component';
import { BlogItemDetailComponent } from './blog-item-detail.component';
import { BlogItemPopupComponent } from './blog-item-dialog.component';
import { BlogItemDeletePopupComponent } from './blog-item-delete-dialog.component';

@Injectable()
export class BlogItemResolvePagingParams implements Resolve<any> {

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

export const blogItemRoute: Routes = [
    {
        path: 'blog-item',
        component: BlogItemComponent,
        resolve: {
            'pagingParams': BlogItemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.blogItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'blog-item/:id',
        component: BlogItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.blogItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const blogItemPopupRoute: Routes = [
    {
        path: 'blog-item-new',
        component: BlogItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.blogItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'blog-item/:id/edit',
        component: BlogItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.blogItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'blog-item/:id/delete',
        component: BlogItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.blogItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
