$.extend($.fn.validatebox.defaults.rules, {
    range: {
        // param[0] = mix length, param[1] = max length, param[2] = field name
        validator: function (value, param) {
            return value.length >= param[0] && value.length <= param[1];
        },
        message: '{2}必須是{0}~{1}碼'
    },
    identicalTo: {
        validator: function (value, identicalTo) {
            return value == $('#' + identicalTo).textbox('getValue');
        },
        message: '二次輸入不相同'
    }
});

$(function () {
    init();
});

function init() {
    initWindow();
    initInputBox();
    initButton();
}

function initWindow() {
    $('#registerWindow').window({
        title: '登入',
        collapsible: false,
        minimizable: false,
        maximizable: false,
        closable: false,
        draggable: false,
        resizable: false
    });
}

function initInputBox() {
    $('#email').textbox({
        label: 'Email：',
        required: true,
        missingMessage: '請輸入Email',
        validType: 'email'
    });
    $('#password').passwordbox({
        label: '密碼：',
        required: true,
        missingMessage: '請輸入密碼',
        validType: {'range': [8, 12, '密碼']}
    });
    $('#confirmPassword').passwordbox({
        label: '確認密碼：',
        required: true,
        missingMessage: '請再次輸入密碼',
        validType: {
            'range': [8, 12, '密碼'],
            'identicalTo': 'password'
        }
    });
}

function initButton() {
    $('#registerBtn').linkbutton({
        onClick: function () {
            let $form = $('#registerForm');
            if ($form.form('validate')) {
                $form.submit();
            }
        }
    });
}

