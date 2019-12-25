'use strict';

$(function () {
    initDataGridOperate('itemGrid', 'itemGridToolBar',
        new DataGridOperateUrlClass('item/save', 'item/update', 'item/delete'));
    initGrid();
});

function initGrid() {
    $('#itemGrid').datagrid({
        url: 'item/list',
        fit: true,
        border: false,
        rownumbers: true,
        toolbar: '#itemGridToolBar',
        pagination: true,
        pageSize: 30,
        pageList: [10, 20, 30],
        columns: getGridColumns(),
        onBeforeSelect: isRowEditing,
        onBeforeCheck: isRowEditing
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
            type: 'textbox',
            options: {
                required: true
            }
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

function addRow() {
    dataGridOperate.addRow();
}

async function deleteRow() {
    dataGridOperate.deleteRow();
}

function editRow(rowId) {
    dataGridOperate.editRow(rowId);
}

function saveEdit() {
    dataGridOperate.saveEdit();
}

function cancelEdit() {
    dataGridOperate.cancelEdit();
}