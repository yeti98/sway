function renderTableBody(matchedCourses){
  let tbody = "";
  matchedCourses.forEach(function (_class){
    tbody +=
    " <tr>\n" +
    "   <td>\n" +
    "     <p>" + _class.classId + "</p>\n" +
    "   </td>\n" +
    "   <td>\n" +
    "     <p>" +_class.name+ "</p>\n" +
    "   </td>\n" +
    "   <td>\n" +
    "     <p>" +_class.courseName+ "</p>\n" +
    "   </td>\n" +
    "   <td>\n" +
    "     <p>" +_class.numberOfStudent+ "</p>\n" +
    "   </td>\n" +
    "   <td>\n" +
    "     <p>" +_class.minScore+ "</p>\n" +
    "   </td>\n" +
    "   <td>\n" +
    "     <div style=\"display: flex\">\n" +
    "       <div id=\"editLesson\" data-class=" + _class.jsonString + "\n" +
    "         onclick=\"javascript:setSelectedObject(this.getAttribute('data-class'));\">\n" +
    "         <a class=\"edit\" href=\"manage/classes/"+_class.id+"\">\n" +
    "           <i class=\"material-icons\"\n" +
    "             data-toggle=\"tooltip\"\n" +
    "             style=\"color: orange\" title=\"Chỉnh sửa\">&#xE254;\n" +
    "           </i>\n" +
    "         </a>\n" +
    "       </div>\n"+
    "       <div id=\"removeLesson\" data-class=" + _class.jsonString + "\n" +
    "         onclick=\"javascript:setSelectedObject(this.getAttribute('data-class'));\">\n" +
    "         <a class=\"delete\" data-toggle=\"modal\" href=\"#deleteClassModal\">\n" +
    "          <i class=\"material-icons\"\n" +
    "            data-toggle=\"tooltip\" style=\"color: red\"\n" +
    "            title=\"Xóa\">&#xE872;\n" +
    "          </i>\n" +
    "        </a>\n" +
    "      </div>\n" +
    "    </div>" +
    "  </td>" +
    " </tr>";

  })
  document.getElementById('tbody').innerHTML = tbody;

}
$(document).ready(function () {
  // Activate tooltip
  $('[data-toggle="tooltip"]').tooltip();

  // $(document).on("click", "#btnAddQuestion", function () {
  //   $("#formAddUserModal").trigger('reset');
  // });

  $(document).on("click", "#removeLesson", function () {
    $(".modal-body #mainContent").text(selectedClass.name);
  });

  $("#formDeleteCourseModal").submit(function (event) {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const id = selectedClass.id;

    request = $.ajax({
      url: "/api/classes/" + id,
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

  $(document).on("click", "#btnSearchClass", function (event) {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");
    $inputs.prop("disabled", true);

    // lấy từ khóa
    const keyword = $('#txtKeyword').val();
    // Xóa bảng
    $('#tblClass tbody').empty();
    // Gửi request
    $.ajax({
      url: "/api/classes/search?query=" + keyword,
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
