function onChange() {
    const password = document.querySelector('input[name=password]');
    const confirm = document.querySelector('input[name=confirm]');
    if (confirm.value === password.value) {
        confirm.setCustomValidity('');
    } else {
        confirm.setCustomValidity('Passwords do not match');
    }
}
$('body').on('click', '.show-password', function(){
    if ($(this).is(':checked')){
        $('#password').attr('type', 'text');
        $('#confirmPassword').attr('type','text')
    } else {
        $('#password').attr('type', 'password');
        $('#confirmPassword').attr('type','password')
    }
});
