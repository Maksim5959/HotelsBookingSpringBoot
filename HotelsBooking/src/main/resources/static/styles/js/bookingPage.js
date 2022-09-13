$(document).ready(function() {
    $(".form-check-input").change(function() {

        if ($('#inlineRadio2').prop("checked")) {
            $('#hide').fadeIn(300);
        } else {
            $('#hide').fadeOut(300);
        }

    });
});