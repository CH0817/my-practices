$(function () {
    init();
    grecaptcha.ready(function () {
        grecaptcha.execute('6LcWN9UUAAAAAJdx2JooRnBbFInBzAvAYz8G32Ia', {action: 'resetPassword'}).then(function (token) {
            console.info(token);
            $.post('v3', {token: token}, function (data) {
                console.info(data);
            });
        });
    });
});

function init() {
    initWindow();
    initInputBox();
    initButton();
}

function initWindow() {
    $('#window').window({
        title: '忘記密碼',
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
}

function initButton() {
    $('#submitBtn').linkbutton({
        onClick: function () {
            let $form = $('#form');
            if ($form.form('validate')) {
                $form.submit();
            }
        }
    });
}