'use strict';

$(function () {
    resetGridOperation();
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
        onBeforeSelect: isEditing,
        onBeforeCheck: isEditing
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
    if (checkEditing()) {
        editingIndex = 0;
        isAdd = true;
        $('#itemGrid').datagrid('insertRow', {
            index: editingIndex,
            row: {}
        }).datagrid('beginEdit', editingIndex);
        upsideDownToolBarButtonStatus('itemGridToolBar');
    }
}

async function deleteRow() {
    if (checkEditing()) {
        let rows = $('#itemGrid').datagrid('getSelections');
        if (rows.length <= 0) {
            $.messager.alert('提示', '請先選擇', 'info');
        } else {
            let isConfirm = await confirmPromise('確定刪除？');
            if (isConfirm) {
                let ids = [];
                for (let row of rows) {
                    ids.push(row.id);
                }
                let result = await saveChange({
                    url: 'item/delete',
                    data: {ids: ids},
                    method: 'delete',
                    traditional: true
                });
                if (result) {
                    for (let row of rows) {
                        $('#itemGrid').datagrid('deleteRow', $('#itemGrid').datagrid('getRowIndex', row));
                    }
                    resetGridOperation('itemGrid');
                    showMessage('刪除成功');
                }
            }
        }
    }
}

function editRow(rowId) {
    if (checkEditing()) {
        editingIndex = getRowIndexByRowId('itemGrid', rowId);
        $('#itemGrid').datagrid('beginEdit', editingIndex);
        upsideDownToolBarButtonStatus('itemGridToolBar');
    }
}

function saveEdit() {
    $('#itemGrid').datagrid('endEdit', editingIndex);
    isAdd ? createItem() : editItem();
}

async function createItem() {
    let id = await saveChange({
        url: 'item/save',
        data: $('#itemGrid').datagrid('getChanges')[0],
        method: 'post'
    }, 'itemGrid');
    if (id) {
        afterCreateItemHandle('itemGrid', id);
        resetGridOperation('itemGrid');
        upsideDownToolBarButtonStatus('itemGridToolBar');
        showMessage('新增成功');
    }
}

async function editItem() {
    let result = await saveChange({
        url: 'item/update',
        data: $('#itemGrid').datagrid('getChanges')[0],
        method: 'put'
    });
    if (result) {
        resetGridOperation('itemGrid');
        upsideDownToolBarButtonStatus('itemGridToolBar');
        showMessage('修改成功');
    }
}

function cancelEdit() {
    $.messager.confirm('確認', '確定取消？', function (r) {
        if (r) {
            $('#itemGrid').datagrid('cancelEdit', editingIndex);
            if (isAdd) {
                $('#itemGrid').datagrid('deleteRow', editingIndex);
            }
            resetGridOperation('itemGrid');
            upsideDownToolBarButtonStatus('itemGridToolBar');
        }
    });
}