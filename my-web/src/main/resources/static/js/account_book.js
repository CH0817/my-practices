'use strict';

let tradeTypes = null;
let accounts = null;
let items = null;
let clearButton = null;

$.fn.datebox.defaults.formatter = function (date) {
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    return year + '/' + month + '/' + day;
}

$.fn.datebox.defaults.parser = function (dateString) {
    var dateNumber = Date.parse(dateString);
    if (!isNaN(dateNumber)) {
        return new Date(dateNumber);
    } else {
        return new Date();
    }
}

$(function () {
    initDateboxExtendButton();
    initGrid();
});

function initDateboxExtendButton() {
    clearButton = $.extend([], $.fn.datebox.defaults.buttons);
    clearButton.splice(1, 0, {
            text: '清除',
            handler: function (target) {
                $(target).datebox('clear').datebox('hidePanel');
            }
        }
    );
}

function initGrid() {
    $('#tradeGrid').datagrid({
        url: 'account-book/trades',
        fit: true,
        border: false,
        rownumbers: true,
        pagination: true,
        pageSize: 30,
        pageList: [10, 30, 50, 70, 100],
        columns: getGridColumns(),
        onEndEdit: function (index, row) {
            // var ed = $(this).datagrid('getEditor', {
            //     index: index,
            //     field: 'productid'
            // });
            // row.productname = $(ed.target).combobox('getText');
        },
        onBeforeEdit: function (index, row) {
            row.editing = true;
            $(this).datagrid('refreshRow', index);
        },
        onAfterEdit: function (index, row) {
            row.editing = false;
            $(this).datagrid('refreshRow', index);
        },
        onCancelEdit: function (index, row) {
            row.editing = false;
            $(this).datagrid('refreshRow', index);
        }
    });
}

function getTradeTypeComboboxData() {
    if (tradeTypes === null) {
        $.ajax({
            async: false,
            url: 'combobox//trade/types',
            success: function (data, textStatusm, jqXHR) {
                tradeTypes = data;
            }
        });
    }
    return tradeTypes;
}

function getAccounts() {
    if (accounts === null) {
        $.ajax({
            async: false,
            url: 'combobox/accounts',
            success: function (data, textStatusm, jqXHR) {
                accounts = data;
            }
        });
    }
    return accounts;
}

function getItems() {
    if (items === null) {
        $.ajax({
            async: false,
            url: 'combobox/items',
            success: function (data, textStatusm, jqXHR) {
                items = data;
            }
        });
    }
    return items;
}

function getTextFromCombobox(comboboxData, value) {
    for (let data of comboboxData) {
        if (data.value === value) {
            return data.text;
        }
    }
    return value;
}

function getGridColumns() {
    return [[{
        field: 'id',
        checkbox: true
    }, {
        field: 'money',
        title: '金額',
        width: 120,
        sortable: true,
        editor: {
            type: 'numberbox',
            options: {
                min: 0,
                precision: 2
            }
        },
        formatter: function (value, rowData, rowIndex) {
            if (value) {
                if (typeof value === 'string') {
                    return value;
                } else {
                    return value.toFixed(2);
                }
            }
            return value;
        }
    }, {
        field: 'trade_type',
        title: '類型',
        width: 80,
        sortable: true,
        editor: {
            type: 'combobox',
            options: {
                limitToList: true,
                editable: false,
                data: getTradeTypeComboboxData()
            }
        },
        formatter: function (value, rowData, rowIndex) {
            return getTextFromCombobox(getTradeTypeComboboxData(), value);
        }
    }, {
        field: 'trade_date',
        title: '日期',
        width: 120,
        sortable: true,
        editor: {
            type: 'datebox',
            options: {
                currentText: '今天',
                closeText: '關閉',
                okText: '確定',
                editable: false,
                buttons: clearButton
            }
        }
    }, {
        field: 'account_id',
        title: '帳戶',
        width: 120,
        sortable: true,
        editor: {
            type: 'combobox',
            options: {
                limitToList: true,
                editable: false,
                data: getAccounts()
            }
        },
        formatter: function (value, rowData, rowIndex) {
            return getTextFromCombobox(getAccounts(), value);
        }
    }, {
        field: 'item_id',
        title: '項目',
        width: 120,
        sortable: true,
        editor: {
            type: 'combobox',
            options: {
                limitToList: true,
                editable: false,
                data: getItems()
            }
        },
        formatter: function (value, rowData, rowIndex) {
            return getTextFromCombobox(getItems(), value);
        }
    }, {
        field: 'operate', title: '操作', align: 'center',
        formatter: function (value, row, index) {
            if (row.editing) {
                var saveLink = '<a href="javascript:void(0);" onclick="saveRow(this)">保存</a> ';
                var cancelLink = '<a href="javascript:void(0);" onclick="cancelRow(this)">取消</a>';
                return saveLink + ' | ' + cancelLink;
            } else {
                var editLink = '<a href="javascript:void(0);" onclick="editRow(this)">修改</a> ';
                var deleteLink = '<a href="javascript:void(0);" onclick="deleteRow(this)">刪除</a>';
                return editLink + ' | ' + deleteLink;
            }
        }
    }]];
}

function getRowIndex(target) {
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}

function saveRow(target) {
    $('#tradeGrid').datagrid('endEdit', getRowIndex(target));
}

function cancelRow(target) {
    $('#tradeGrid').datagrid('cancelEdit', getRowIndex(target));
}

function editRow(target) {
    $('#tradeGrid').datagrid('beginEdit', getRowIndex(target));
}

function deleteRow(target) {
    $.messager.confirm('確認', '確定刪除？', function (r) {
        if (r) {
            $('#tradeGrid').datagrid('deleteRow', getRowIndex(target));
        }
    });
}