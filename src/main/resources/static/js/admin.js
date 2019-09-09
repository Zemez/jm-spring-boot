$(document).ready(function () {
    $.get('/admin/all', function (users) {
        $('#users').html(users);
        $('.user').on('click', function (event) {
            event.preventDefault();
            var href = '/admin/' + $(this).attr('id');
            $.get(href, function (user) {
                $('#user-modal-body').html(user);
            });
            $('#user-modal').modal();
        })
    });
    $.get('/admin/create', function (user) {
        $('#create').html(user);
    });
});
