import {Route} from '@angular/router';
import {FirstSettingsComponent} from './first-settings.component';

export const firstSettingsRoute: Route = {
    path: 'setup',
    component: FirstSettingsComponent,
    data: {
        // authorities: ['ROLE_ADMIN'],
        pageTitle: 'setup.title'
    },
    // canActivate: [UserRouteAccessService]
};
