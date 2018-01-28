/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { BlogTestModule } from '../../../test.module';
import { KeyWordDetailComponent } from '../../../../../../main/webapp/app/entities/key-word/key-word-detail.component';
import { KeyWordService } from '../../../../../../main/webapp/app/entities/key-word/key-word.service';
import { KeyWord } from '../../../../../../main/webapp/app/entities/key-word/key-word.model';

describe('Component Tests', () => {

    describe('KeyWord Management Detail Component', () => {
        let comp: KeyWordDetailComponent;
        let fixture: ComponentFixture<KeyWordDetailComponent>;
        let service: KeyWordService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [KeyWordDetailComponent],
                providers: [
                    KeyWordService
                ]
            })
            .overrideTemplate(KeyWordDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KeyWordDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KeyWordService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new KeyWord(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.keyWord).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
