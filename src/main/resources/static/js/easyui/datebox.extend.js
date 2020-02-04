'use strict';

$.fn.datebox.defaults.formatter = function (date) {
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    return year + '/' + month + '/' + day;
};

$.fn.datebox.defaults.parser = function (dateString) {
    let dateNumber = Date.parse(dateString);
    if (!isNaN(dateNumber)) {
        return new Date(dateNumber);
    } else {
        return new Date();
    }
};

function addClearButton() {
    let clearButton = $.extend([], $.fn.datebox.defaults.buttons);
    clearButton.splice(1, 0, {
            text: '清除',
            handler: function (target) {
                $(target).datebox('clear').datebox('hidePanel');
            }
        }
    );
    return clearButton;
}