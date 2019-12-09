'use strict';

function initMenu() {
    $('#functionTree').tree({
        url: 'function/menu',
        onClick: function (node) {
            console.info(node);
            let attributes = node.attributes;
            // FIXME 要過濾 logout url
            if (attributes !== undefined) {
                $('body').layout('panel', 'center').panel('refresh', attributes.url);
            }
        }
    });
}

$(function () {
    initMenu();
});