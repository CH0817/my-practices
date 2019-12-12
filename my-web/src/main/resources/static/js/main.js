'use strict';

function initMenu() {
    $('#functionTree').tree({
        url: 'function/menu',
        onClick: function (node) {
            let attributes = node.attributes;
            // FIXME 要過濾 logout url
            if (attributes !== undefined && attributes.url !== 'logout') {
                $('body').layout('panel', 'center')
                    .panel('refresh', attributes.url);
            } else if (attributes.url === 'logout') {
                $('<form action="' + attributes.url + '"></form>').appendTo('body').submit();
            }
        }
    });
}

$(function () {
    initMenu();
});