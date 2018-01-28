import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KeyWord } from './key-word.model';
import { KeyWordPopupService } from './key-word-popup.service';
import { KeyWordService } from './key-word.service';
import { BlogItem, BlogItemService } from '../blog-item';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-key-word-dialog',
    templateUrl: './key-word-dialog.component.html'
})
export class KeyWordDialogComponent implements OnInit {

    keyWord: KeyWord;
    isSaving: boolean;

    blogitems: BlogItem[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private keyWordService: KeyWordService,
        private blogItemService: BlogItemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.blogItemService.query()
            .subscribe((res: ResponseWrapper) => { this.blogitems = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.keyWord.id !== undefined) {
            this.subscribeToSaveResponse(
                this.keyWordService.update(this.keyWord));
        } else {
            this.subscribeToSaveResponse(
                this.keyWordService.create(this.keyWord));
        }
    }

    private subscribeToSaveResponse(result: Observable<KeyWord>) {
        result.subscribe((res: KeyWord) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: KeyWord) {
        this.eventManager.broadcast({ name: 'keyWordListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBlogItemById(index: number, item: BlogItem) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-key-word-popup',
    template: ''
})
export class KeyWordPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private keyWordPopupService: KeyWordPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.keyWordPopupService
                    .open(KeyWordDialogComponent as Component, params['id']);
            } else {
                this.keyWordPopupService
                    .open(KeyWordDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
