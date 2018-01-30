import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {BlogSharedModule} from '../shared';

import {FirstSettingsComponent, setupState} from './';

@NgModule({
    imports: [
        BlogSharedModule,
        RouterModule.forChild(setupState)
    ],
    declarations: [
        FirstSettingsComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogSetupModule {
}
