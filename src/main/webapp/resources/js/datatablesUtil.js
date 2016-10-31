function makeEditable() {
    $('.delete').click(function () {
        deleteRow($(this).closest('tr').attr('id'));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $('#filter').submit(filter);

    $('.box').change(function () {
        var id = $(this).closest('tr').attr('id');
        var parent = this;
        if ($(parent).is(':checked')) {
            $.get(ajaxUrl + "toggle/" + id, {enabled:"true"}, function (data) {
                $(parent).closest('tr').fadeTo(300, 1);
                successNoty("User enabled");
            });
        } else {
            $.get(ajaxUrl + "toggle/" + id, {enabled:"false"}, function (data) {
                $(parent).closest('tr').fadeTo(300, 0.3);
                successNoty("User disabled");
            });
        }
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function filter() {
    var form = $('#filter');
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: form.serialize(),
        success: function (data) {
            redraw(data);
            $('.delete').click(function () {
                deleteRow($(this).closest('tr').attr('id'));
            });
        }
    });
    return false;
}

function redraw(data) {
    datatableApi.clear();
    $.each(data, function (key, item) {
        datatableApi.row.add(item);
    });
    datatableApi.draw();
}

function add() {
    $('#id').val(null);
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    var form = $('#filter').val();
    if (form != undefined) {
        filter();
    } else {
        $.get(ajaxUrl, function (data) {
            redraw(data);
        });
    }
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
