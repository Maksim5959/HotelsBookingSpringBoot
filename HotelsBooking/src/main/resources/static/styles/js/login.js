$('body').on('click', '.show-password', function(){
    if ($(this).is(':checked')){
        $('#password').attr('type', 'text');
    } else {
        $('#password').attr('type', 'password');
    }
});