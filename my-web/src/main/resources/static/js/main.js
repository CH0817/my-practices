'use strict';

function initMenu() {
    $('#functionTree').tree({
        url: 'function/menu',
        onClick: function (node) {
            if (node.id === 13) {
                let centerPanel = $('body').layout('panel', 'center');
                $(centerPanel).panel('refresh', 'url');
            }
        }
    });
}

$(function () {
    initMenu();
});