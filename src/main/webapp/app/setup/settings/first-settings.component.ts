import {Component, ElementRef, OnInit} from '@angular/core';
import {JhiDataUtils, JhiLanguageService} from 'ng-jhipster';

import {JhiLanguageHelper, Principal, SetupService, User, UserService} from '../../shared';
import {Blog} from '../../entities/blog';

@Component({
    selector: 'jhi-setup',
    templateUrl: './first-settings.component.html'
})
export class FirstSettingsComponent implements OnInit {
    error: string;
    success: string;
    user: User;
    blog: Blog;
    languages: any[];

    constructor(private account: UserService,
                private principal: Principal,
                private languageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private setupService: SetupService,
                private dataUtils: JhiDataUtils,
                private elementRef: ElementRef) {
    }

    ngOnInit() {
        this.blog = new Blog();

        this.setupService.findIdAdmin().subscribe((id) => {
            this.user = new User(id, '', '', '', '', true, '', ['ROLE_ADMIN'], '', new Date(), '', new Date(), '');
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    save() {
        this.setupService.updateAdmin(this.user).subscribe(() => {
            this.error = null;
            this.success = 'OK';
            this.principal.identity(true).then((user) => {
                this.user = user;
                // todo : création du blog, maintenant qu'on est loggué en admin
            });

            this.languageService.getCurrent().then((current) => {
                if (this.user.langKey !== current) {
                    this.languageService.changeLanguage(this.user.langKey);
                }
            });
        }, () => {
            this.success = null;
            this.error = 'ERROR';
        });

    }


    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.blog, this.elementRef, field, fieldContentType, idInput);
    }

}
