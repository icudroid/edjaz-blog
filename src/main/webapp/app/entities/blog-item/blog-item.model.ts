import { BaseEntity } from './../../shared';

export const enum ItemStatus {
    'PUBLISHED',
    'TRASH',
    'DRAFT'
}

export const enum ItemVisibility {
    'PUBLIC',
    'PRIVATE',
    'PROTECTED'
}

export class BlogItem implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public url?: string,
        public text?: any,
        public staus?: ItemStatus,
        public created?: any,
        public updated?: any,
        public imageContentType?: string,
        public image?: any,
        public visiblity?: ItemVisibility,
        public password?: string,
        public blogId?: number,
        public comments?: BaseEntity[],
        public keywords?: BaseEntity[],
        public blogItemId?: number,
        public histories?: BaseEntity[],
        public authorId?: number,
        public tags?: BaseEntity[],
    ) {
    }
}
