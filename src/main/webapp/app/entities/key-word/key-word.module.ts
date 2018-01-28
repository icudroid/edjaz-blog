import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from '../../shared';
import {
    KeyWordService,
    KeyWordPopupService,
    KeyWordComponent,
    KeyWordDetailComponent,
    KeyWordDialogComponent,
    KeyWordPopupComponent,
    KeyWordDeletePopupComponent,
    KeyWordDeleteDialogComponent,
    keyWordRoute,
    keyWordPopupRoute,
    KeyWordResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...keyWordRoute,
    ...keyWordPopupRoute,
];

@NgModule({
    imports: [
        BlogSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        KeyWordComponent,
        KeyWordDetailComponent,
        KeyWordDialogComponent,
        KeyWordDeleteDialogComponent,
        KeyWordPopupComponent,
        KeyWordDeletePopupComponent,
    ],
    entryComponents: [
        KeyWordComponent,
        KeyWordDialogComponent,
        KeyWordPopupComponent,
        KeyWordDeleteDialogComponent,
        KeyWordDeletePopupComponent,
    ],
    providers: [
        KeyWordService,
        KeyWordPopupService,
        KeyWordResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogKeyWordModule {}
