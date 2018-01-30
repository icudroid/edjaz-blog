import {Routes} from '@angular/router';
import {firstSettingsRoute} from './';

const SETUP_ROUTES = [
    firstSettingsRoute
];

export const setupState: Routes = [{
    path: '',
    children: SETUP_ROUTES
}];
