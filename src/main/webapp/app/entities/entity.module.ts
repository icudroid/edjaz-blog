import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BlogBlogModule } from './blog/blog.module';
import { BlogBlogItemModule } from './blog-item/blog-item.module';
import { BlogKeyWordModule } from './key-word/key-word.module';
import { BlogTagModule } from './tag/tag.module';
import { BlogCommentBlogItemModule } from './comment-blog-item/comment-blog-item.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BlogBlogModule,
        BlogBlogItemModule,
        BlogKeyWordModule,
        BlogTagModule,
        BlogCommentBlogItemModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogEntityModule {}
