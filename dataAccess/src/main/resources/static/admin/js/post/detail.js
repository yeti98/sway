var coverPhoto = undefined;

function encodeImageFileAsURL() {

    var filesSelected = document.getElementById("coverPhoto").files;
    if (filesSelected.length > 0) {
        var fileToLoad = filesSelected[0];

        var fileReader = new FileReader();

        fileReader.onload = function(fileLoadedEvent) {
            coverPhoto = fileLoadedEvent.target.result; // <--- data: base64
            console.log(coverPhoto);

        }
        fileReader.readAsDataURL(fileToLoad);
    }
}

function getExtension(filename) {
    var parts = filename.split('.');
    return parts[parts.length - 1];
}

function isImage(filename) {
    var ext = getExtension(filename);
    switch (ext.toLowerCase()) {
        case 'jpg':
        case 'jpeg':
        case 'gif':
        case 'bmp':
        case 'png':
        case 'webp':
            //etc
            return true;
    }
    return false;
}

function getEditPost(){

    selectedPost = JSON.parse(selectedPost);

    $("#title").val(selectedPost.title);
    $("#idPost").val(selectedPost.id);
    $("#menu").val(selectedPost.menu);
    $("#inputContent").summernote('code', '');
    $("#inputContent").summernote('code', selectedPost.contents);
    $("#btnedit").css("display","block");


}

$(document).ready(function () {


    $(document).on("click","#btnedit", function () {
        sessionStorage.removeItem("editPost");
        const id = $("#idPost").val();
        const title = $("#title").val();
        const menu = $("#menu").val();
        const contents = $('#inputContent').summernote('code');


        const payLoad = JSON.stringify({
            id,title,menu,contents
        })

        console.log(payLoad);


        $.ajax({
            url: "/api/posts/" + id,
            type: "put",
            data: payLoad,
            contentType: "application/json; charset=utf-8",
            success: function (msg) {

                console.log(msg)
                window.location.href="/admin/manage/posts"
            },
            error: function (msg) {

                alert("Đăng bài thất bại: \n", msg);
                window.location.reload();
            }
        });

    })
})
