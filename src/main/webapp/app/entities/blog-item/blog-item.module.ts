import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from '../../shared';
import { BlogAdminModule } from '../../admin/admin.module';
import {
    BlogItemService,
    BlogItemPopupService,
    BlogItemComponent,
    BlogItemDetailComponent,
    BlogItemDialogComponent,
    BlogItemPopupComponent,
    BlogItemDeletePopupComponent,
    BlogItemDeleteDialogComponent,
    blogItemRoute,
    blogItemPopupRoute,
    BlogItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...blogItemRoute,
    ...blogItemPopupRoute,
];

@NgModule({
    imports: [
        BlogSharedModule,
        BlogAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BlogItemComponent,
        BlogItemDetailComponent,
        BlogItemDialogComponent,
        BlogItemDeleteDialogComponent,
        BlogItemPopupComponent,
        BlogItemDeletePopupComponent,
    ],
    entryComponents: [
        BlogItemComponent,
        BlogItemDialogComponent,
        BlogItemPopupComponent,
        BlogItemDeleteDialogComponent,
        BlogItemDeletePopupComponent,
    ],
    providers: [
        BlogItemService,
        BlogItemPopupService,
        BlogItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogBlogItemModule {}
