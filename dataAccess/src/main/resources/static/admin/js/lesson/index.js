function renderTableBody(matchedCourses){
  var tbody = "";
  matchedCourses.forEach(function (lesson){
    tbody += "<tr>\n" +
        "                  <td>\n" +
        "                    <p>" +lesson.lessonId+ "</p>\n" +
        "                  </td>\n" +
        "                  <td>\n" +
        "                    <p>" +lesson.name+ "</p>\n" +
        "                  </td>\n" +
        "                  <td>\n" +
        "                    <p>" + lesson.numberOfTest + "</p>\n" +
        "                  </td>\n" +
        "                  <td>\n" +
        "                    <div style=\"display: flex\">\n" +
        "                      <div id=\"editLesson\" data-homework=" +lesson.jsonString + "\n" +
        "                           onclick=\"javascript:setSelectedObject(this.getAttribute('data-homework'));\">\n" +
        "                        <a class=\"edit\" href=\"manage/lessons/"+ lesson.id +"\">\n" +
        "                          <i class=\"material-icons\"\n" +
        "                             data-toggle=\"tooltip\"\n" +
        "                             style=\"color: orange\" title=\"Chỉnh sửa\">&#xE254;\n" +
        "                          </i>\n" +
        "                        </a>\n" +
        "                      </div>\n" +
        "                      <div id=\"removeLesson\" data-homework=" +lesson.jsonString + "\n" +
        "                           onclick=\"javascript:setSelectedObject(this.getAttribute('data-lesson'));\">\n" +
        "                        <a class=\"delete\" data-toggle=\"modal\" href=\"#deleteLessonModal\">\n" +
        "                          <i class=\"material-icons\"\n" +
        "                             data-toggle=\"tooltip\" style=\"color: red\"\n" +
        "                             title=\"Xóa\">&#xE872;\n" +
        "                          </i>\n" +
        "                        </a>\n" +
        "                      </div>\n" +
        "                    </div>\n" +
        "                  </td>\n" +
        "                </tr>"
  });
  document.getElementById('tbody').innerHTML = tbody;
}
$(document).ready(function () {
  // Activate tooltip
  $('[data-toggle="tooltip"]').tooltip();

  // $(document).on("click", "#btnAddQuestion", function () {
  //   $("#formAddUserModal").trigger('reset');
  // });

  $(document).on("click", "#removeLesson", function () {
    $(".modal-body #mainContent").text(selectedLesson.name);
  });

  $("#formDeleteLessonModal").submit(function (event) {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const id = selectedLesson.id;

    request = $.ajax({
      url: "/api/lessons/" + id,
      type: "delete",
      contentType: "application/json; charset=utf-8",
      success: function (msg) {
        $inputs.prop("disabled", false);
        console.log(msg);
        window.location.reload();
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Xóa thất bại: \n", msg);
        window.location.reload();
      }
    });
  });

  $(document).on("click", "#btnSearchLesson", function (event) {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");
    $inputs.prop("disabled", true);

    // lấy từ khóa
    const keyword = $('#txtKeyword').val();
    // Xóa bảng
    $('#tblLesson tbody').empty();
    // Gửi request
    $.ajax({
      url: "/api/lessons/search?query=" + keyword,
      type: "get",
      contentType: "application/json; charset=utf-8",
      success: function (matchedCourses) {
        $inputs.prop("disabled", false);
        renderTableBody(matchedCourses);
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Không tìm thấy: \n", msg);
      }
    });
  });
});
