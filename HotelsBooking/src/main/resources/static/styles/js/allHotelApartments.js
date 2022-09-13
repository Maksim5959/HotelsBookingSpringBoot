$(document).ready(function(){
    $(".modal").on('hidden.bs.modal', function(){
        location.reload();
    });
});

