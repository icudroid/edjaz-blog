import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { KeyWord } from './key-word.model';
import { KeyWordPopupService } from './key-word-popup.service';
import { KeyWordService } from './key-word.service';

@Component({
    selector: 'jhi-key-word-delete-dialog',
    templateUrl: './key-word-delete-dialog.component.html'
})
export class KeyWordDeleteDialogComponent {

    keyWord: KeyWord;

    constructor(
        private keyWordService: KeyWordService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.keyWordService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'keyWordListModification',
                content: 'Deleted an keyWord'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-key-word-delete-popup',
    template: ''
})
export class KeyWordDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private keyWordPopupService: KeyWordPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.keyWordPopupService
                .open(KeyWordDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
