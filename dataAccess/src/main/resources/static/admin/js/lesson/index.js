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
    if (keyword.length == 0) {
      $inputs.prop("disabled", false);
      return ;
    }
    // Xóa bảng
    $('#tblLesson tbody').empty();
    // Gửi request
    $.ajax({
      url: "/api/lessons/search?query=" + keyword,
      type: "get",
      contentType: "application/json; charset=utf-8",
      success: function (matchedLessons) {
        $inputs.prop("disabled", false);
        renderTableBody(matchedLessons);
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Không tìm thấy: \n", msg);
      }
    });
  });
});
