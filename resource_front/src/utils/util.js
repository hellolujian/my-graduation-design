export const hello = () => {
    return 'hello';
}

//去除input空格
export const removeTrim = (value) => {
    return value.trim();
}

export const isNotNullORBlank = (value) => {
    if (value.trim().length == 0 || value.indexOf(" ") >= 0) {
        return false;
    } else {
        return true;
    }
}