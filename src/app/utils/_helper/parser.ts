export function toFormData(val, formData = new FormData, namespace = '') {
    console.log({ val });
    if ((typeof val !== 'undefined') && val !== null) {
        if (val instanceof Date) {
            formData.append(namespace, val.toISOString());
        } else if (val instanceof Array) {
            for (let i = 0; i < val.length; i++) {
                toFormData(val[i], formData, namespace + '[' + i + ']');
            }
        } else if (typeof val === 'object' && !(val instanceof File)) {
            for (let propertyName in val) {
                if (val.hasOwnProperty(propertyName)) {
                    toFormData(val[propertyName], formData, namespace ? `${namespace}[${propertyName}]` : propertyName);
                }
            }
        } else if (val instanceof File) {
            formData.append(namespace, val);
        } else {
            formData.append(namespace, val.toString());
        }
    }
    return formData;
}

function buildFormData(formData, data, parentKey) {
    if (data && typeof data === 'object' && !(data instanceof Date) && !(data instanceof File)) {
        Object.keys(data).forEach(key => {
            buildFormData(formData, data[key], parentKey ? `${parentKey}[${key}]` : key);
        });
    } else {
        const value = data == null ? '' : data;

        formData.append(parentKey, value);
    }
}

export function jsonToFormData(data) {
    const formData = new FormData();
    buildFormData(formData, data, '');
    return formData;
}