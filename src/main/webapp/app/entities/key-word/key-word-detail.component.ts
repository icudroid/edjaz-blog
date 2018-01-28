import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { KeyWord } from './key-word.model';
import { KeyWordService } from './key-word.service';

@Component({
    selector: 'jhi-key-word-detail',
    templateUrl: './key-word-detail.component.html'
})
export class KeyWordDetailComponent implements OnInit, OnDestroy {

    keyWord: KeyWord;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private keyWordService: KeyWordService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKeyWords();
    }

    load(id) {
        this.keyWordService.find(id).subscribe((keyWord) => {
            this.keyWord = keyWord;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKeyWords() {
        this.eventSubscriber = this.eventManager.subscribe(
            'keyWordListModification',
            (response) => this.load(this.keyWord.id)
        );
    }
}
