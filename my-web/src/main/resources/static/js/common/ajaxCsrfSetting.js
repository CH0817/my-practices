const csrfHeaders = {};
csrfHeaders[$('meta[name=\'_csrf\']').attr('content')] = $('meta[name=\'_csrf_header\']').attr('content');

$.ajaxSetup({
    headers: csrfHeaders
});