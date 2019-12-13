'use strict';

$(function () {
    initGrid();
});

function initGrid() {
    $('#tradeGrid').datagrid({
        url: 'account/list',
        fit: true,
        border: false,
        rownumbers: true,
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
        title: '金額',
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
        sortable: true
    }, {
        field: 'operate',
        title: '操作',
        align: 'center',
        width: 80,
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

function saveRow() {

}

function cancelRow() {

}

function editRow() {

}

function deleteRow() {

}