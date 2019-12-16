'use strict';

let clearButton = null;

let comboboxData = {
    tradeTypes: null,
    accounts: null,
    items: null
}

$(function () {
    initMenu();
    initDateboxExtendButton();
});

function initMenu() {
    $('#functionTree').tree({
        url: 'function/menu',
        onClick: function (node) {
            let attributes = node.attributes;
            if (attributes !== undefined && attributes.url !== 'logout') {
                $('body').layout('panel', 'center').panel('clear').panel('refresh', attributes.url);
            } else if (attributes.url === 'logout') {
                $('<form action="' + attributes.url + '"></form>').appendTo('body').submit();
            }
        }
    });
}

function initDateboxExtendButton() {
    if (clearButton !== null) {
        clearButton = $.extend([], $.fn.datebox.defaults.buttons);
        clearButton.splice(1, 0, {
                text: '清除',
                handler: function (target) {
                    $(target).datebox('clear').datebox('hidePanel');
                }
            }
        );
    }
}

function getTradeTypeComboboxData() {
    if (comboboxData.tradeTypes === null) {
        comboboxData.tradeTypes = sendNonAyncAjax('combobox//trade/types');
    }
    return comboboxData.tradeTypes;
}

function getAccounts() {
    if (comboboxData.accounts === null) {
        comboboxData.accounts = sendNonAyncAjax('combobox/accounts');
    }
    return comboboxData.accounts;
}

function getItems() {
    if (comboboxData.items === null) {
        comboboxData.items = sendNonAyncAjax('combobox/items');
    }
    return comboboxData.items;
}

function sendNonAyncAjax(url) {
    let result = null;
    $.ajax({
        async: false,
        url: url,
        success: function (data, textStatusm, jqXHR) {
            result = data;
        }
    });
    return result;
}