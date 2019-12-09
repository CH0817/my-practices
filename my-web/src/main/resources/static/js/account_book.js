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
        field: 'ck',
        checkbox: true
    }, {
        field: 'id',
        hidden: true
    }, {
        field: 'accountId',
        hidden: true
    }, {
        field: 'itemId',
        hidden: true
    }, {
        field: 'moneyString',
        title: '金額',
        sortable: true
    }, {
        field: 'tradeType',
        title: '類型',
        sortable: true
    }, {
        field: 'tradeDate',
        title: '日期',
        sortable: true
    }, {
        field: 'accountName',
        title: '帳戶',
        sortable: true
    }, {
        field: 'itemName',
        title: '項目',
        sortable: true
    }]];
}