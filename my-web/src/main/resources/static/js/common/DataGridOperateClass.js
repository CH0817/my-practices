class DataGridOperateClass {
    constructor(gridId, toolBarId, operateUrls) {
        this.gridId = gridId;
        this.grid = $('#' + gridId);
        this.toolBarId = toolBarId;
        this.editingIndex = null;
        this.isAdd = false;
        this.operateUrls = operateUrls;
    }

    isEditing() {
        return this.editingIndex === null;
    }

    checkEditing() {
        let result = this.isEditing();
        if (!result) {
            $.messager.alert('提示', '資料編輯中', 'info');
        }
        return result;
    }

    upsideDownToolBarButtonStatus() {
        $.map($('#' + this.toolBarId + ' a'), function (linkButton) {
            let method = 'enable';
            if (!$(linkButton).linkbutton('options').disabled) {
                method = 'disable';
            }
            $(linkButton).linkbutton(method);
        });
    }

    getRowIndexByRowId(rowId) {
        for (let row of this.grid.datagrid('getRows')) {
            if (row.id == rowId) {
                return this.grid.datagrid('getRowIndex', row);
            }
        }
    }

    confirmPromise(message) {
        return new Promise((resolve, reject) => {
            $.messager.confirm('確認', message, function (r) {
                resolve(r);
            });
        });
    }

    addRow() {
        if (this.checkEditing()) {
            this.editingIndex = 0;
            this.isAdd = true;
            this.grid.datagrid('insertRow', {
                index: this.editingIndex,
                row: {}
            }).datagrid('beginEdit', this.editingIndex);
            this.upsideDownToolBarButtonStatus();
        }
    }

    editRow(rowId) {
        if (this.checkEditing()) {
            this.editingIndex = this.getRowIndexByRowId(rowId);
            this.grid.datagrid('beginEdit', this.editingIndex);
            this.upsideDownToolBarButtonStatus();
        }
    }

    async deleteRow() {
        if (this.checkEditing()) {
            let rows = this.grid.datagrid('getSelections');
            if (rows.length <= 0) {
                $.messager.alert('提示', '請先選擇', 'info');
            } else {
                let isConfirm = await this.confirmPromise('確定刪除？');
                if (isConfirm) {
                    let ids = [];
                    for (let row of rows) {
                        ids.push(row.id);
                    }
                    let result = await this.saveChange({
                        url: this.operateUrls.deleteUrl,
                        data: {ids: ids},
                        method: 'delete',
                        traditional: true
                    });
                    if (result) {
                        for (let row of rows) {
                            this.grid.datagrid('deleteRow', this.grid.datagrid('getRowIndex', row));
                        }
                        this.resetGridOperation();
                        this.showMessage('刪除成功');
                    }
                }
            }
        }
    }

    async cancelEdit() {
        let isConfirm = await this.confirmPromise('確定取消？');
        if (isConfirm) {
            this.grid.datagrid('cancelEdit', this.editingIndex);
            if (this.isAdd) {
                this.grid.datagrid('deleteRow', this.editingIndex);
            }
            this.resetGridOperation();
            this.upsideDownToolBarButtonStatus();
        }
    }

    saveEdit() {
        this.grid.datagrid('endEdit', this.editingIndex);
        this.isAdd ? this.remoteCreate() : this.remoteEdit();
    }

    async remoteCreate() {
        let insertedId = await this.saveChange({
            url: this.operateUrls.saveUrl,
            data: this.grid.datagrid('getChanges')[0],
            method: 'post'
        });
        if (insertedId) {
            this.afterRemoteCreateHandle(insertedId);
            this.resetGridOperation();
            this.upsideDownToolBarButtonStatus();
            this.showMessage('新增成功');
        }
    }

    async remoteEdit() {
        let result = await this.saveChange({
            url: this.operateUrls.updateUrl,
            data: this.grid.datagrid('getChanges')[0],
            method: 'put'
        });
        if (result) {
            this.resetGridOperation();
            this.upsideDownToolBarButtonStatus();
            this.showMessage('修改成功');
        }
    }

    saveChange(settings = {}) {
        let editingIndex = this.editingIndex;
        let gridId = this.gridId;
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
                $('#' + gridId).datagrid('beginEdit', editingIndex);
            },
            success: function (data, textStatus, jqXHR) {
                return data;
            }
        };
        return $.ajax($.extend(defaultSettings, settings));
    }

    afterRemoteCreateHandle(remoteId) {
        this.grid.datagrid('updateRow', {
            index: this.editingIndex,
            row: {
                id: remoteId
            }
        });
    }

    resetGridOperation() {
        this.editingIndex = null;
        this.isAdd = false;
        this.grid.datagrid('acceptChanges');
    }

    showMessage(message = '') {
        $.messager.show({
            title: '訊息',
            msg: message,
            showType: 'fade'
        });
    }
};