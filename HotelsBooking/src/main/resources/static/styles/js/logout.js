let form = $('.logout-form'),
    submitLink = form.children('.submit-link');

submitLink.click(function (e) {
    e.preventDefault();
    form.submit();
});