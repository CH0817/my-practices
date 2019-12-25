'use strict';

$(function () {
    initDataGridOperate('accountGrid', 'accountGridToolBar',
        new DataGridOperateUrlClass('account/save', 'account/update', 'account/delete'));
    initGrid();
});

function initGrid() {
    $('#accountGrid').datagrid({
        url: 'account/list',
        fit: true,
        border: false,
        rownumbers: true,
        toolbar: '#accountGridToolBar',
        pagination: true,
        pageSize: 30,
        pageList: [10, 20, 30],
        columns: getGridColumns()
    });
}

function getGridColumns() {
    return [[{
        field: 'id',
        checkbox: true
    }, {
        field: 'name',
        title: '名稱',
        width: 120,
        sortable: true,
        editor: {
            type: 'textbox'
        }
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
        field: 'account_type_id',
        title: '帳戶類型',
        width: 120,
        sortable: true,
        editor: {
            type: 'combobox',
            options: {
                limitToList: true,
                editable: false,
                data: getAccountTypeComboboxData()
            }
        },
        formatter: function (value, rowData, rowIndex) {
            return getTextFromCombobox(getAccountTypeComboboxData(), value);
        }
    }, {
        field: 'operate',
        title: '操作',
        align: 'center',
        width: 60,
        formatter: function (value, row, index) {
            return '<a href="javascript:void(0);" onclick="editRow(\'' + row.id + '\')">修改</a>';
        }
    }]];
}