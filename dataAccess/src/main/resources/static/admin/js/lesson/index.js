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
});
