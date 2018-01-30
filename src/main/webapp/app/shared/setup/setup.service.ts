import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {User} from '../';
import {SERVER_API_URL} from '../../app.constants';
import {ResponseWrapper} from '../model/response-wrapper.model';

@Injectable()
export class SetupService {
    private resourceUrl = SERVER_API_URL + 'api/setup';

    constructor(private http: Http) {
    }

    findIdAdmin(): Observable<number> {
        return this.http.get(this.resourceUrl + '/user')
            .map((res: Response) => res.json());
    }

    updateAdmin(user: User): Observable<ResponseWrapper> {
        return this.http.put(this.resourceUrl + '/user', user)
            .map((res: Response) => this.convertResponse(res));
    }

    isFirstSetup(): Observable<boolean> {
        return this.http.get(this.resourceUrl).map((res: Response) => res.json());
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
