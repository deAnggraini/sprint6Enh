import { AbstractControl, ValidationErrors, AsyncValidator, NG_ASYNC_VALIDATORS } from '@angular/forms'
import { Observable, of } from 'rxjs';
import { Directive } from '@angular/core';
import { debounceTime, map } from 'rxjs/operators';

@Directive({
    selector: '[customAsyncValidator]',
    providers: [{
        provide: NG_ASYNC_VALIDATORS,
        useExisting: AsyncUniqValidator,
        multi: true
    }]
})
export class AsyncUniqValidator implements AsyncValidator {

    api: Observable<any>;

    constructor(api: Observable<any>) {
        this.api = api;
    }

    validate(control: AbstractControl): Promise<ValidationErrors> | Observable<ValidationErrors> {
        const { value } = control;
        return this.api.pipe(
            debounceTime(500),
            map((data: any) => {
                if (!data.isValid) return ({ 'InValid': true })
            })
        )
    }

    registerOnValidatorChange?(fn: () => void): void {
        throw new Error("Method not implemented.");
    }

    static create(api: Observable<any>): AsyncUniqValidator {
        return new AsyncUniqValidator(api);
    }
}