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

function upsideDownToolBarButtonStatus() {
    $.map($('#itemGridToolBar a'), function (linkbutton) {
        let method = 'enable';
        if (!$(linkbutton).linkbutton('options').disabled) {
            method = 'disable';
        }
        $(linkbutton).linkbutton(method);
    });
}

function addRow() {
    if (checkEditing()) {
        editingIndex = 0;
        isAdd = true;
        $('#itemGrid').datagrid('insertRow', {
            index: editingIndex,
            row: {}
        }).datagrid('beginEdit', editingIndex);
        upsideDownToolBarButtonStatus();
    }
}

function deleteRow() {
    if (checkEditing()) {
        let rows = $('#itemGrid').datagrid('getSelections');
        if (rows.length <= 0) {
            $.messager.alert('提示', '請先選擇', 'info');
        } else {
            $.messager.confirm('確認', '確定刪除？', function (r) {
                if (r) {
                    for (let row of rows) {
                        $('#itemGrid').datagrid('deleteRow', $('#itemGrid').datagrid('getRowIndex', row));
                    }
                    resetGridOperation('itemGrid');
                }
            });
        }
    }
}

function editRow(rowId) {
    if (checkEditing()) {
        editingIndex = getRowIndexByRowId(rowId);
        $('#itemGrid').datagrid('beginEdit', editingIndex);
        upsideDownToolBarButtonStatus();
    }
}

async function saveEdit() {
    $('#itemGrid').datagrid('endEdit', editingIndex);
    let rows = $('#itemGrid').datagrid('getChanges');
    let url = isAdd ? 'item/save' : '/item/update';
    let id = await saveChange(url, rows[0]);
    if (id) {
        if (isAdd) {
            $('#itemGrid').datagrid('updateRow', {
                index: editingIndex,
                row: {
                    id: id
                }
            });
        }
        resetGridOperation('itemGrid');
        upsideDownToolBarButtonStatus();
    }
}

function saveChange(url, data) {
    return $.ajax({
        url: url,
        data: data,
        beforeSend: function (jqXHR, settings) {
            $.messager.progress();
        },
        complete: function (jqXHR, textStatus) {
            $.messager.progress('close');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // TODO 加強這裡
            if (jqXHR.status == 400) {
                $.messager.alert('錯誤', jqXHR.responseText, 'error');
            }
        },
        success: function (data, textStatus, jqXHR) {
            return data;
        }
    });
}

function cancelEdit() {
    $.messager.confirm('確認', '確定取消？', function (r) {
        if (r) {
            $('#itemGrid').datagrid('cancelEdit', editingIndex);
            if (isAdd) {
                $('#itemGrid').datagrid('deleteRow', editingIndex);
            }
            resetGridOperation('itemGrid');
            upsideDownToolBarButtonStatus();
        }
    });
}

function getRowIndexByRowId(rowId) {
    for (let row of $('#itemGrid').datagrid('getRows')) {
        if (row.id == rowId) {
            return $('#itemGrid').datagrid('getRowIndex', row);
        }
    }
}