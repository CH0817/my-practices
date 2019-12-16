'use strict';

let clearButton = null;

let comboboxData = {
    tradeTypes: null,
    accounts: null,
    items: null
}

$(function () {
    initMenu();
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