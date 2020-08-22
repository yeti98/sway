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
    selectedPost = undefined;
    selectedPost = JSON.parse(sessionStorage.getItem("editPost"));
    console.log(selectedPost);


    if (selectedPost != undefined) {
        $("#title").val(selectedPost.title);
        $("#idPost").val(selectedPost.id);
        $("#menu").val(selectedPost.Menu);
        $("#inputContent").summernote('code', '');
        $("#inputContent").summernote('code', selectedPost.contents);
        $("#btnedit").css("display","block");
        $("#btnsubmit").css("display","none");
    }
    else {
        $("#btnedit").css("display","none");
        $("#btnsubmit").css("display","block");
    }
}

$(document).ready(function () {


    // Activate tooltip
    $('[data-toggle="tooltip"]').tooltip();

    $(document).on("click", "#btnsubmit", function () {
        const coverPhotoUrl = $("#coverPhoto").val() || undefined;

        if(coverPhotoUrl == undefined){
            alert("Không để trống cover photo");
        }
        else
        if(!isImage(coverPhotoUrl)){
            alert("Chỉ chọn ảnh.");
        }
        else {
            const title = $("#title").val();
            const menu = $("#menu").val();
            const contents = $('#inputContent').summernote('code');


            const payLoad = JSON.stringify({
                title,menu,contents
            })

            console.log(payLoad);


            $.ajax({
                url: "/api/posts/",
                type: "get",
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
        }
    });

    $(document).on("click","#removePost", function () {
        document.getElementById('delete_title').innerHTML= "Tiêu đề: " + selectedPost.title;

    })



    $(document).on("click","#confirmDelete", function () {
        $.ajax({
            url: "/api/posts/" + selectedPost.id,
            type: "delete",
            contentType: "application/json; charset=utf-8",
            success: function (msg) {

                console.log(msg);
                window.location.href="/admin/manage/posts";
            },
            error: function (msg) {

                alert("Xoá bài thất bại: \n", msg);
                window.location.reload();
            }
        });

    })

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

    $(document).on("click","#createPost", function () {
        sessionStorage.removeItem("editPost");
    })
})