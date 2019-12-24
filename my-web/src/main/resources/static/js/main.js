'use strict';

let editingIndex = null;
let isAdd = false;
let clearButton = null;

let comboboxData = {
    tradeTypes: null,
    accounts: null,
    items: null,
    accountTypes: null
};

$(function () {
    setAjaxDefaultCsrf();
    initMenu();
});

function setAjaxDefaultCsrf() {
    let token = $('meta[name=\'_csrf\']').attr('content');
    let header = $('meta[name=\'_csrf_header\']').attr('content');

    let headers = {};
    headers[header] = token;

    $.ajaxSetup({
        headers: headers
    });
}

function initMenu() {
    $('#functionTree').tree({
        url: 'function/menu',
        onClick: function (node) {
            let attributes = node.attributes;
            if (attributes !== undefined && attributes.url !== 'logout') {
                $('body').layout('panel', 'center').panel('clear').panel('refresh', attributes.url);
            } else if (attributes.url === 'logout') {
                $('#logoutForm').submit();
            }
        }
    });
}

function getTradeTypeComboboxData() {
    if (comboboxData.tradeTypes === null) {
        comboboxData.tradeTypes = sendNonAyncAjax('combobox/trade/types');
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

function getAccountTypeComboboxData() {
    if (comboboxData.accountTypes === null) {
        comboboxData.accountTypes = sendNonAyncAjax('combobox/account/types');
    }
    return comboboxData.accountTypes;
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

function getTextFromCombobox(comboboxData, value) {
    for (let data of comboboxData) {
        if (data.value === value) {
            return data.text;
        }
    }
    return value;
}

function resetGridOperation(gridId) {
    editingIndex = null;
    isAdd = null;
    $('#' + gridId).datagrid('acceptChanges');
}

function isEditing() {
    return editingIndex === null;
}

function checkEditing() {
    let result = isEditing();
    if (!result) {
        $.messager.alert('提示', '資料編輯中', 'info');
    }
    return result;
}