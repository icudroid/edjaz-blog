/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { BlogTestModule } from '../../../test.module';
import { KeyWordComponent } from '../../../../../../main/webapp/app/entities/key-word/key-word.component';
import { KeyWordService } from '../../../../../../main/webapp/app/entities/key-word/key-word.service';
import { KeyWord } from '../../../../../../main/webapp/app/entities/key-word/key-word.model';

describe('Component Tests', () => {

    describe('KeyWord Management Component', () => {
        let comp: KeyWordComponent;
        let fixture: ComponentFixture<KeyWordComponent>;
        let service: KeyWordService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [KeyWordComponent],
                providers: [
                    KeyWordService
                ]
            })
            .overrideTemplate(KeyWordComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KeyWordComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KeyWordService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new KeyWord(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.keyWords[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
