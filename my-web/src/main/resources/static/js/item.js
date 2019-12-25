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

async function deleteRow() {
    if (checkEditing()) {
        let rows = $('#itemGrid').datagrid('getSelections');
        if (rows.length <= 0) {
            $.messager.alert('提示', '請先選擇', 'info');
        } else {
            let isConfirm = await confirm('確定刪除？');
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

function confirm(message) {
    return new Promise((resolve, reject) => {
        $.messager.confirm('確認', message, function (r) {
            resolve(r);
        });
    });
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
    let id = await saveChange({
        url: isAdd ? 'item/save' : '/item/update',
        data: $('#itemGrid').datagrid('getChanges')[0],
        method: 'post'
    });
    if (id) {
        isAddHandle(id);
        resetGridOperation('itemGrid');
        upsideDownToolBarButtonStatus();
        showMessage('新增成功');
    }
}

function isAddHandle(id) {
    if (isAdd) {
        $('#itemGrid').datagrid('updateRow', {
            index: editingIndex,
            row: {
                id: id
            }
        });
    }
}

function saveChange(settings = {}) {
    verifyAjaxSettings(settings);
    let defaultSettings = {
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
            $('#itemGrid').datagrid('beginEdit', editingIndex);
        },
        success: function (data, textStatus, jqXHR) {
            return data;
        }
    };
    return $.ajax($.extend(defaultSettings, settings));
}

function verifyAjaxSettings(settings) {
    if (!settings.url) {
        throw 'AJAX settings must contain url';
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

function showMessage(message = '') {
    $.messager.show({
        title: '訊息',
        msg: message,
        showType: 'fade'
    });
}