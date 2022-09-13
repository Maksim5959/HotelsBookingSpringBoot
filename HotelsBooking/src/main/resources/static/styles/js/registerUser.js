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

function validatePassword() {
    const newPassword = document.querySelector('input[name=password]');
    const regularExpression = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,50}$/;
    const form = document.querySelector('form');
    form.addEventListener('submit', function (evt) {
        evt.preventDefault();
        if (!regularExpression.test(newPassword.value)) {
            alert("password should contain at least one number and one special character");
            return;
        }
        this.submit();
    });
}
