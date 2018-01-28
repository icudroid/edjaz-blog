import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('KeyWord e2e test', () => {

    let navBarPage: NavBarPage;
    let keyWordDialogPage: KeyWordDialogPage;
    let keyWordComponentsPage: KeyWordComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load KeyWords', () => {
        navBarPage.goToEntity('key-word');
        keyWordComponentsPage = new KeyWordComponentsPage();
        expect(keyWordComponentsPage.getTitle())
            .toMatch(/blogApp.keyWord.home.title/);

    });

    it('should load create KeyWord dialog', () => {
        keyWordComponentsPage.clickOnCreateButton();
        keyWordDialogPage = new KeyWordDialogPage();
        expect(keyWordDialogPage.getModalTitle())
            .toMatch(/blogApp.keyWord.home.createOrEditLabel/);
        keyWordDialogPage.close();
    });

    it('should create and save KeyWords', () => {
        keyWordComponentsPage.clickOnCreateButton();
        keyWordDialogPage.setWordInput('word');
        expect(keyWordDialogPage.getWordInput()).toMatch('word');
        keyWordDialogPage.blogItemSelectLastOption();
        keyWordDialogPage.save();
        expect(keyWordDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class KeyWordComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-key-word div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class KeyWordDialogPage {
    modalTitle = element(by.css('h4#myKeyWordLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    wordInput = element(by.css('input#field_word'));
    blogItemSelect = element(by.css('select#field_blogItem'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setWordInput = function(word) {
        this.wordInput.sendKeys(word);
    }

    getWordInput = function() {
        return this.wordInput.getAttribute('value');
    }

    blogItemSelectLastOption = function() {
        this.blogItemSelect.all(by.tagName('option')).last().click();
    }

    blogItemSelectOption = function(option) {
        this.blogItemSelect.sendKeys(option);
    }

    getBlogItemSelect = function() {
        return this.blogItemSelect;
    }

    getBlogItemSelectedOption = function() {
        return this.blogItemSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
