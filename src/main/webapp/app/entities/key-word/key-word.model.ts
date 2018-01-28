import { BaseEntity } from './../../shared';

export class KeyWord implements BaseEntity {
    constructor(
        public id?: number,
        public word?: string,
        public blogItemId?: number,
    ) {
    }
}
