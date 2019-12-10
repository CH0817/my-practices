'use strict';

$(function () {
    initGrid();
});

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
            var ed = $(this).datagrid('getEditor', {
                index: index,
                field: 'productid'
            });
            row.productname = $(ed.target).combobox('getText');
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
        },
        onBeforeLoad: function (param) {
            switch (param.sort) {
                case "moneyString":
                    param.sort = 'money';
                    break;
                case "accountName":
                    param.sort = 'account_id';
                    break;
                case "itemId":
                    param.sort = 'item_id';
                    break;
                default:
                    // FIXME field 必須匹配 table column
                    break;
            }
        }
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
        }
    }, {
        field: 'tradeType',
        title: '類型',
        width: 80,
        sortable: true,
        editor: {
            type: 'combobox',
            options: {
                valueField: 'id',
                textField: 'name',
                limitToList: true,
                editable: false,
                data: [{
                    "id": 1,
                    "name": "收入",
                    "selected": true
                }, {
                    "id": 2,
                    "name": "支出"
                }, {
                    "id": 3,
                    "name": "轉帳"
                }]
            }
        }
    }, {
        field: 'tradeDate',
        title: '日期',
        width: 120,
        sortable: true,
        editor: {
            type: 'datebox',
            options: {
                currentText: '今天',
                closeText: '關閉',
                okText: '確定'
            }
        }
    }, {
        field: 'accountId',
        title: '帳戶',
        width: 120,
        sortable: true,
        editor: {
            type: 'combobox',
            options: {
                valueField: 'id',
                textField: 'name',
                limitToList: true,
                editable: false,
                data: [{
                    "id": 1,
                    "name": "現金",
                    "selected": true
                }, {
                    "id": 2,
                    "name": "銀行"
                }]
            }
        }
    }, {
        field: 'itemId',
        title: '項目',
        width: 120,
        sortable: true,
        editor: {
            type: 'combobox',
            options: {
                valueField: 'id',
                textField: 'name',
                limitToList: true,
                editable: false,
                data: [{
                    "id": 1,
                    "name": "吃飯",
                    "selected": true
                }, {
                    "id": 2,
                    "name": "睡覺"
                }]
            }
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
    // $.messager.confirm('Confirm', 'Are you sure?', function (r) {
    //     if (r) {
    $('#tradeGrid').datagrid('deleteRow', getRowIndex(target));
    //     }
    // });
}