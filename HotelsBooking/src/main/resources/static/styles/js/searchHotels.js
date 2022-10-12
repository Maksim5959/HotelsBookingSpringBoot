// $('#clear-button').click(function() {
//     $('input[name=hotelsFilter]').attr('checked',false);
// });

$('#price-up').click(function () {
    $('input[id=hotels-filter]').attr('value', 'PRICE_UP');
});
$('#price-down').click(function () {
    $('input[id=hotels-filter]').attr('value', 'PRICE_DOWN');
});
$('#name-up').click(function () {
    $('input[id=hotels-filter]').attr('value', 'NAME_UP');
});
$('#name-down').click(function () {
    $('input[id=hotels-filter]').attr('value', 'NAME_DOWN');
});
$('#rating-up').click(function () {
    $('input[id=hotels-filter]').attr('value', 'RATING_UP');
});
$('#rating-down').click(function () {
    $('input[id=hotels-filter]').attr('value', 'RATING_DOWN');
});
$('#clear-filters').click(function () {
    $('input[id=hotels-filter]').attr('value', 'DEFAULT');
});
