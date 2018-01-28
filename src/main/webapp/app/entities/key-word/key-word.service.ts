import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { KeyWord } from './key-word.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KeyWordService {

    private resourceUrl =  SERVER_API_URL + 'api/key-words';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/key-words';

    constructor(private http: Http) { }

    create(keyWord: KeyWord): Observable<KeyWord> {
        const copy = this.convert(keyWord);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(keyWord: KeyWord): Observable<KeyWord> {
        const copy = this.convert(keyWord);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<KeyWord> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to KeyWord.
     */
    private convertItemFromServer(json: any): KeyWord {
        const entity: KeyWord = Object.assign(new KeyWord(), json);
        return entity;
    }

    /**
     * Convert a KeyWord to a JSON which can be sent to the server.
     */
    private convert(keyWord: KeyWord): KeyWord {
        const copy: KeyWord = Object.assign({}, keyWord);
        return copy;
    }
}
