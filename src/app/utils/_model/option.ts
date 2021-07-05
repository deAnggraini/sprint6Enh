export interface Option {
    value: string,
    text: string,
    children?: Option[], // combo box with accordion
}