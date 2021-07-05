export interface Option {
    id: string,
    value: string,
    text: string,
    children?: Option[], // combo box with accordion
}