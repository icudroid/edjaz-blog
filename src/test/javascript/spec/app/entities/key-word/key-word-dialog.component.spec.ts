/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BlogTestModule } from '../../../test.module';
import { KeyWordDialogComponent } from '../../../../../../main/webapp/app/entities/key-word/key-word-dialog.component';
import { KeyWordService } from '../../../../../../main/webapp/app/entities/key-word/key-word.service';
import { KeyWord } from '../../../../../../main/webapp/app/entities/key-word/key-word.model';
import { BlogItemService } from '../../../../../../main/webapp/app/entities/blog-item';

describe('Component Tests', () => {

    describe('KeyWord Management Dialog Component', () => {
        let comp: KeyWordDialogComponent;
        let fixture: ComponentFixture<KeyWordDialogComponent>;
        let service: KeyWordService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [KeyWordDialogComponent],
                providers: [
                    BlogItemService,
                    KeyWordService
                ]
            })
            .overrideTemplate(KeyWordDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KeyWordDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KeyWordService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new KeyWord(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.keyWord = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'keyWordListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new KeyWord();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.keyWord = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'keyWordListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
