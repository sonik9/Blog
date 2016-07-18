/**
 * Created by Vitalii on 7/18/2016.
 */
$(document).ready(function () {

    $('#edit').click(function () {
        NProgress.start();
        $('.edit1').toggle();
        $('.show1').toggle();
        NProgress.done();

    });


    $('img[name=userpic]').click(function () {
        $('#image').click();
    });

    $("#resizable").resizable({
        handles: 'se, sw, nw, ne, e ,w, n, s',
        helper: "ui-resizable-helper",
        ghost: true,
        aspectRatio: true,
        /*maxHeight: 232,
         maxWidth: 232,*/
        containment: "#imagePreview"
    }).draggable();

    $("#image").change(function () {
        $("#modalPhotoPreview").modal("show");
        showimagepreview(this);


    });

    $("#saveImage").click(function () {
        var left = $("#resizable").position().left-15;
        var top = $("#resizable").position().top-15;
        var height = $("#resizable").height();
        var width = $("#resizable").width();
        var origHeight = $("#imagePreview").height();
        var origWidth = $("#imagePreview").width();


//            /* //files=event.target.files;
        var files = $('#image')[0].files;

        var form = new FormData();
        form.append("image", files[0]);
        form.append("left",left);
        form.append("top", top);
        form.append("height", height);
        form.append("width", width);
        form.append("origHeight", origHeight);
        form.append("origWidth", origWidth);

        jQuery.ajax({
                //dataType:'json',
                data: form,
                type: "POST",
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                url: "${rootUrl}${firstname}.${lastname}/uploadphoto",
                success: function (data) {
                    alert(data);
                },
                error: function (xhr, status, errorThrown) {
                    alert(xhr + ' ' + status + '. ' + errorThrown);
                }

            }
        );
    });
    function showimagepreview(input) {
        if (input.files && input.files[0]) {
            var filerdr = new FileReader();
            filerdr.onload = function (e) {

                $('#imagePreview').attr('src', e.target.result);
            };
            filerdr.readAsDataURL(input.files[0]);
        }
    }
});