let widgetId = null;

$(function () {
    init();
    setAjaxDefaultCsrf();
});

function setAjaxDefaultCsrf() {
    let token = $('meta[name=\'_csrf\']').attr('content');
    let header = $('meta[name=\'_csrf_header\']').attr('content');

    let headers = {};
    headers[header] = token;

    $.ajaxSetup({
        headers: headers
    });
}

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
        resizable: false,
        onOpen: loadReCaptcha
    });
}

function loadReCaptcha() {
    let script = document.createElement('script');
    script.src = 'https://www.google.com/recaptcha/api.js?onload=loadReCaptchCallback&render=explicit&hl=zh-TW';
    document.head.appendChild(script);
}

function loadReCaptchCallback() {
    widgetId = grecaptcha.render('html_element', {
        'sitekey': '6LdiftUUAAAAAGnFHKqVo5bdh5mWUyMr2Z5eRA8n'
    });
};

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
            let token = getToken();
            let $form = $('#form');
            if ($form.form('validate') && token) {
                $form.submit();
            }
        }
    });
}

function getToken() {
    let token = grecaptcha.getResponse(widgetId);
    if (!token) {
        $.messager.alert('提示', '請先勾選我不是機器人');
    }
    return token;
}