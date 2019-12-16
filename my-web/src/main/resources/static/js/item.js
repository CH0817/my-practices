$(function () {
    initGrid();
});

function initGrid() {
    $('#itemGrid').datagrid({
        url: 'item/list',
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
        title: '名稱',
        width: 120,
        sortable: true,
        editor: {
            type: 'textbox'
        }
    }, {
        field: 'operate',
        title: '操作',
        align: 'center',
        width: 80,
        formatter: function (value, row, index) {
            if (row.editing) {
                let saveLink = '<a href="javascript:void(0);" onclick="saveRow(this)">保存</a> ';
                let cancelLink = '<a href="javascript:void(0);" onclick="cancelRow(this)">取消</a>';
                return saveLink + ' | ' + cancelLink;
            } else {
                let editLink = '<a href="javascript:void(0);" onclick="editRow(this)">修改</a> ';
                let deleteLink = '<a href="javascript:void(0);" onclick="deleteRow(this)">刪除</a>';
                return editLink + ' | ' + deleteLink;
            }
        }
    }]];
}