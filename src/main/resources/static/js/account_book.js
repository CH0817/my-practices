'use strict';

$(function () {
    initDataGridOperate('tradeGrid', 'tradeGridToolBar',
        new DataGridOperateUrlClass('account-book/save', 'account-book/update', 'account-book/delete'));
    initGrid();
});

function initGrid() {
    $('#tradeGrid').datagrid({
        url: 'account-book/trades',
        fit: true,
        border: false,
        rownumbers: true,
        toolbar: '#tradeGridToolBar',
        pagination: true,
        pageSize: 30,
        pageList: [10, 30, 50, 70, 100],
        columns: getGridColumns()
    });
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
                buttons: addClearButton()
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
            return '<a href="javascript:void(0);" onclick="editRow(\'' + row.id + '\')">修改</a>';
        }
    }]];
}