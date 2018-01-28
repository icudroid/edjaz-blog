import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from '../../shared';
import { BlogAdminModule } from '../../admin/admin.module';
import {
    CommentBlogItemService,
    CommentBlogItemPopupService,
    CommentBlogItemComponent,
    CommentBlogItemDetailComponent,
    CommentBlogItemDialogComponent,
    CommentBlogItemPopupComponent,
    CommentBlogItemDeletePopupComponent,
    CommentBlogItemDeleteDialogComponent,
    commentBlogItemRoute,
    commentBlogItemPopupRoute,
    CommentBlogItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...commentBlogItemRoute,
    ...commentBlogItemPopupRoute,
];

@NgModule({
    imports: [
        BlogSharedModule,
        BlogAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CommentBlogItemComponent,
        CommentBlogItemDetailComponent,
        CommentBlogItemDialogComponent,
        CommentBlogItemDeleteDialogComponent,
        CommentBlogItemPopupComponent,
        CommentBlogItemDeletePopupComponent,
    ],
    entryComponents: [
        CommentBlogItemComponent,
        CommentBlogItemDialogComponent,
        CommentBlogItemPopupComponent,
        CommentBlogItemDeleteDialogComponent,
        CommentBlogItemDeletePopupComponent,
    ],
    providers: [
        CommentBlogItemService,
        CommentBlogItemPopupService,
        CommentBlogItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogCommentBlogItemModule {}
