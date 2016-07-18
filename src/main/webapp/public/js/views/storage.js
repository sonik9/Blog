/**
 * Created by Vitalii on 15/09/2015.
 */
$(document).ready(function () {
    NProgress.configure({easing: 'ease', speed: 500});
    updateFrame();
});

//        $("#fileUpload").on("change", handleFileSelect);

var homePath = $("#homePath").val(),
    storedFiles = [],
    deletedFiles = [],
    uploadFiles = [],
    uploadButton = $('<button/>')
        .addClass('btn btn-primary btn-upload')
        .text('Upload')
        .on('click', function () {
            var form = new FormData(),
                i = 0,
                $this = $(this);
            name = $this.attr('name');

            for (i = 0; i < uploadFiles.length; i++) {
                if (uploadFiles[i].name === name) {
                    form.append('files', uploadFiles[i]);
                    break;
                }
            }
            jQuery.ajax({
                    data: form,
                    type: "POST",
                    enctype: 'multipart/form-data',
                    processData: false,
                    contentType: false,
                    url: homePath+"/storage/upload",
                    xhr: function () {
                        var xhr = new window.XMLHttpRequest();
                        //Upload progress
                        xhr.upload.addEventListener("progress", function (evt) {
                            if (evt.lengthComputable) {
                                var percentComplete = evt.loaded / evt.total;
                                NProgress.start();
                                console.log(percentComplete);
                            }
                        }, false);
                        //Download progress
                        xhr.addEventListener("progress", function (evt) {
                            if (evt.lengthComputable) {
                                var percentComplete = evt.loaded / evt.total;
                                //Do something with download progress
                                NProgress.set(percentComplete);
                                console.log(percentComplete);
                            }
                        }, false);
                        return xhr;
                    },
                    success: function (data) {
                        //alert(data);
                        uploadFiles.splice(i, 1);
                        $this.parent().parent().parent().remove();
                        if (uploadFiles.length === 0) {
                            $("#filesUpload").find('tbody').append("No files");
                        }
                        updateFrame();
                        NProgress.done();
                    },
                    error: function (xhr, status, errorThrown) {
                        alert(xhr + ' ' + status + '. ' + errorThrown);
                    }

                }
            );
        }),
    cancelButton = $('<button/>')
        .addClass('btn btn-warning cancel')
        .text('Cancel')
        .bind('click', function () {
            var name = $(this).attr('name');
            for (var i = 0; i < uploadFiles.length; i++) {
                if (uploadFiles[i].name === name) {
                    uploadFiles.splice(i, 1);
                    break;
                }
            }
            if (uploadFiles.length === 0) {
                $("#filesUpload").find('tbody').append("No files");
            }
            $(this).parent().parent().parent().remove();
        });


function clearAll() {
    $("#filesUpload").find('tbody').children().remove();
    $("#fileUpload").val("");
    uploadFiles.splice(0, uploadFiles.length);
    $("#filesUpload").find('tbody').append("No files");
    $(this).prop("disabled", true);
    $("#uploadall").prop("disabled", true);
    NProgress.done();
}



function showimagepreview(input) {
    if (input.files && input.files[0]) {
        if (uploadFiles.length === 0) {
            $("#filesUpload").empty();
        }
        $.each(input.files, function (index, file) {
            if (file.type.match("\.(gif|jpg|jpeg|tiff|png)$")) {
                uploadFiles.push(file);
                var filerdr = new FileReader(),
                //_URL = window.URL || window.webkitURL,
                    image = new Image();
                filerdr.onload = function (e) {
                    image.src = e.target.result;
                    tr = document.createElement('tr');
                    tdImage = document.createElement("td");
                    $(tdImage).addClass("col-xs-4 col-center-block").append("<img src='" + e.target.result + "' class='img-responsive uploadImagePrev'/>");
                    tdName = document.createElement("td");
                    $(tdName).addClass("col-xs-4 col-center-block").append("<p>" + file.name + "</p>").append("<p>" + sizeH(file.size) + "</p>").append("<p>" + image.width + "x" + image.height + "</p>");
                    tdButtons = document.createElement("td");
                    divButtons = document.createElement("div");
                    $(divButtons).addClass("btn-group pull-right").append(cancelButton.attr("name", file.name).clone(true)).append(uploadButton.attr("name", file.name).clone(true));
                    $(tdButtons).addClass("col-xs-4").append(divButtons);
                    $(tr).attr("name", file.name).append(tdImage).append(tdName).append(tdButtons);
                    $("#filesUpload").append(tr);
                    $("#clearall").prop("disabled", false);
                    $("#uploadall").prop("disabled", false);
                }
                filerdr.readAsDataURL(input.files[index]);
                //image.src=_URL.createOjectURL(input.files[index]);
            } else {
                alert("Wrong type of image!");
            }
        });
    }
}
function updateFrame() {
    $("#filelist").load(homePath + "/storage/download",function(response, status, xhr) {
            if ($("#filelist").find('div').length === 0) { //is(":empty")){
                $('#deleteall').prop("disabled", true);
                $(this).html("No files. Please upload new files.")
            }
            else
                $('#deleteall').prop("disabled", false)
    });
}


$("#fileUpload").change(function () {
    showimagepreview(this);
});


$("#clearall").click(function () {
    clearAll();
});

$("#uploadall").click(function () {
    var form = new FormData();
    for (var i = 0, len = uploadFiles.length; i < len; i++) {
        form.append('files', uploadFiles[i]);
    }
    jQuery.ajax({
            data: form,
            type: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            url: homePath+"/storage/upload",
            xhr: function () {
                var xhr = new window.XMLHttpRequest();
                //Upload progress
                xhr.upload.addEventListener("progress", function (evt) {
                    if (evt.lengthComputable) {
                        var percentComplete = evt.loaded / evt.total;
                        NProgress.start();
                        console.log(percentComplete);
                    }
                }, false);
                //Download progress
                xhr.addEventListener("progress", function (evt) {
                    if (evt.lengthComputable) {
                        var percentComplete = evt.loaded / evt.total;
                        //Do something with download progress
                        NProgress.set(percentComplete);
                        console.log(percentComplete);
                    }
                }, false);
                return xhr;
            },
            success: function (data) {
                clearAll();
                updateFrame();
                NProgress.done();
            },
            error: function (xhr, status, errorThrown) {
                alert(xhr + ' ' + status + '. ' + errorThrown);
                NProgress.done();
            }


        }
    );
});

/*function filespaste(file) {
 xhr = new XMLHttpRequest();
 xhr.open('GET', file, true);
 xhr.responseType = 'blob';
 xhr.onload = function (e) {
 if (this.status == 200) {
 var blob = this.response;

 var img = document.createElement('img');
 img.onload = function (e) {
 window.URL.revokeObjectURL(img.src); // Clean up after yourself.
 var constr = $('<div />')
 .addClass('col-xs-6')
 .css("padding-right", 0)
 .append($('<div />')
 .addClass('files')
 .append($('<div />')
 .addClass('col-xs-5 thmb')
 .append($('<a />')
 .addClass('readingglass')
 .attr('href', file).attr('target', '_blank'))
 .append($('<img />')
 .addClass('img-responsive img-rounded')
 .attr('src', file)))
 .append($('<div />')
 .addClass('info col-xs-7')
 .append($('<a />')
 .addClass('file-delete')
 .attr('title', 'Delete')
 .attr('id', file)
 .attr('href', '#')
 .val('❌'))
 .append($('<div />')
 .addClass('filename')
 .append(img.name))
 .append($('<div />')
 .addClass('meta')
 .append($('<span />')
 .addClass('file_size')
 .append(size(blob.size)))
 .append('<span />')
 .addClass('image_size').append(img.width + "x" + img.height))
 .append($('<div />')
 .addClass('copypasta')
 .append($('<input />')
 .addClass('html_code')
 .attr('type', 'text')
 .val('<img src="' + file + '"/>'))
 .append($('<input />')
 .addClass('url')
 .attr('type', 'text').val(file)))));
 $("#filelist").append(constr.clone(true));

 };
 img.src = window.URL.createObjectURL(blob);


 }
 };
 xhr.send();
 }




 function dowloadFile() {
 jQuery.ajax({
 //data: form,
 type: "POST",
 enctype: 'multipart/form-data',
 processData: false,
 contentType: false,
 url: homePath + "/storage/download",

 success: function (data) {
 NProgress.start();
 data.forEach(function (file, i) {
 filespaste(file);
 });
 NProgress.done();
 },
 error: function (xhr, status, errorThrown) {
 alert(xhr + ' ' + status + '. ' + errorThrown);
 NProgress.done();
 }


 }
 );
 }*/
function deleteFileClick(file) {
    deletedFiles.push(file);
    deleteFiles(deletedFiles);
}

function deleteFiles(files) {
    var result = confirm("Are you sure, that you want to delete this file?");
    if (result) {
        var form = new FormData();
        files.forEach(function (item, i) {
            form.append('files', item.name);
        });
        jQuery.ajax({
                data: form,
                type: "POST",
                //enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                url: homePath + "/storage/delete",
                xhr: function () {
                    var xhr = new window.XMLHttpRequest();
                    //Upload progress
                    xhr.upload.addEventListener("progress", function (evt) {
                        if (evt.lengthComputable) {
                            var percentComplete = evt.loaded / evt.total;
                            NProgress.start();
                            console.log(percentComplete);
                        }
                    }, false);
                    //Download progress
                    xhr.addEventListener("progress", function (evt) {
                        if (evt.lengthComputable) {
                            var percentComplete = evt.loaded / evt.total;
                            //Do something with download progress
                            NProgress.set(percentComplete);
                            console.log(percentComplete);
                        }
                    }, false);
                    return xhr;
                },
                success: function (data) {
                    updateFrame();
                    alert("Files: "+data+" was deleted!");
                    NProgress.done();
                },
                error: function (xhr, status, errorThrown) {
                    alert(xhr + ' ' + status + '. ' + errorThrown);
                    NProgress.done();
                }


            }
        );
    }
}

//});