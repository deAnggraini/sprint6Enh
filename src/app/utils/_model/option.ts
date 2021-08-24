export interface Option {
    id: string,
    value: string,
    text: string,
    children?: Option[], // combo box with accordion
}

export function craeteEmptyOption(): Option {
    return { id: '', text: '', value: '' }
}