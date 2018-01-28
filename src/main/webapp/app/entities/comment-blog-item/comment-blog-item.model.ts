import { BaseEntity } from './../../shared';

export const enum CommentStatus {
    'DESAPPROUVED',
    'CREATED',
    'MODIFY',
    'SPAM',
    'TRASH'
}

export class CommentBlogItem implements BaseEntity {
    constructor(
        public id?: number,
        public text?: string,
        public created?: any,
        public updated?: any,
        public status?: CommentStatus,
        public blogItemId?: number,
        public commentBlogItemId?: number,
        public replies?: BaseEntity[],
        public authorId?: number,
    ) {
    }
}
