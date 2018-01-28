import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CommentBlogItemComponent } from './comment-blog-item.component';
import { CommentBlogItemDetailComponent } from './comment-blog-item-detail.component';
import { CommentBlogItemPopupComponent } from './comment-blog-item-dialog.component';
import { CommentBlogItemDeletePopupComponent } from './comment-blog-item-delete-dialog.component';

@Injectable()
export class CommentBlogItemResolvePagingParams implements Resolve<any> {

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

export const commentBlogItemRoute: Routes = [
    {
        path: 'comment-blog-item',
        component: CommentBlogItemComponent,
        resolve: {
            'pagingParams': CommentBlogItemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.commentBlogItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'comment-blog-item/:id',
        component: CommentBlogItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.commentBlogItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commentBlogItemPopupRoute: Routes = [
    {
        path: 'comment-blog-item-new',
        component: CommentBlogItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.commentBlogItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comment-blog-item/:id/edit',
        component: CommentBlogItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.commentBlogItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comment-blog-item/:id/delete',
        component: CommentBlogItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.commentBlogItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
