function renderTableBody(matchedCourses){
    var tbody = "";
    matchedCourses.forEach(function (post){
        post.jsonString = post.jsonString.replaceAll(' ','###DEV_CULI###');
        tbody+="" +
            "<tr>\n" +
            "  <td>\n" +
            "    <p>"+post.readableMenu+"</p>\n" +
            "  </td>\n" +
            "  <td>\n" +
            "    <p>"+post.title+"</p>\n" +
            "  </td>\n" +
            "  <td>\n" +
            "    <p>"+post.createDay+"</p>\n" +
            "  </td>\n" +
            "  <td style=\"display: flex\">\n" +
            "    <div id=\"editPost\">\n" +
            "      <a class=\"edit\" href=\"/admin/manage/posts/"+post.id+"\">\n" +
            "        <i class=\"material-icons\"\n" +
            "           data-toggle=\"tooltip\"\n" +
            "           title=\"Chỉnh sửa\">&#xE254;\n" +
            "        </i>\n" +
            "      </a>\n" +
            "    </div>\n" +
            "    <div id=\"removePost\" data-post="+post.jsonString+"\n" +
            "         onclick=\"javascript:setSelectedObject(this.getAttribute('data-post'));\">\n" +
            "      <a class=\"delete\" data-toggle=\"modal\" href=\"#deletePostModal\">\n" +
            "        <i class=\"material-icons\"\n" +
            "           data-toggle=\"tooltip\"\n" +
            "           title=\"Xóa\">&#xE872;\n" +
            "        </i>\n" +
            "      </a>\n" +
            "    </div>\n" +
            "    <div id=\"viewPost\">\n" +
            "      <a class=\"view\" href=\"#\">\n" +
            "        <i class=\"material-icons\"\n" +
            "           data-toggle=\"tooltip\"\n" +
            "           title=\"Xem\">arrow_forward\n" +
            "        </i>\n" +
            "      </a>\n" +
            "    </div>\n" +
            "  </td>\n" +
            "</tr>";
    });
    console.log(tbody);
    document.getElementById('tbody').innerHTML = tbody;
}
$(document).ready(function () {
    // Activate tooltip
    $('[data-toggle="tooltip"]').tooltip();


    $(document).on("click","#removePost", function () {
        document.getElementById('delete_title').innerHTML= "Tiêu đề: " + selectedPost.title;
    });



    $(document).on("click","#confirmDelete", function () {
        console.log("/api/posts/" + selectedPost.id);
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

    });

    $(document).on("click","#createPost", function () {
        sessionStorage.removeItem("editPost");
    });

    $(document).on("click", "#btnSearchPost", function (event) {
        event.preventDefault();

        const $form = $(this);
        const $inputs = $form.find("input, select, button, textarea");
        $inputs.prop("disabled", true);

        // lấy từ khóa
        const keyword = $('#txtKeyword').val();
        // Xóa bảng
        $('#tblPost tbody').empty();
        // Gửi request
        $.ajax({
            url: "/api/posts/search?query=" + keyword,
            type: "get",
            contentType: "application/json; charset=utf-8",
            success: function (matchedCourses) {
                $inputs.prop("disabled", false);
                console.log(matchedCourses);
                renderTableBody(matchedCourses);
            },
            error: function (msg) {
                $inputs.prop("disabled", false);
                alert("Không tìm thấy: \n", msg);
            }
        });
    });
});