function isEmpty(str) {
  return (!str || 0 === str.length);
}

function isBlank(str) {
  return (!str || /^\s*$/.test(str));
}

function isAllBlank(...strs) {
  for (let idx in strs) {
    if (!isBlank(String(strs[idx]))) {
      return false;
    }
  }
  return true;
}

$(document).ready(function () {
  // Activate tooltip
  $('[data-toggle="tooltip"]').tooltip();

  // Select/Deselect checkboxes
  var checkbox = $('table tbody input[type="checkbox"]');
  $("#selectAll").click(function () {
    if (this.checked) {
      checkbox.each(function () {
        this.checked = true;
      });
    } else {
      checkbox.each(function () {
        this.checked = false;
      });
    }
  });
  checkbox.click(function () {
    if (!this.checked) {
      $("#selectAll").prop("checked", false);
    }
  });

  // $(document).on("click", "#btnAddQuestion", function () {
  //   $("#formAddUserModal").trigger('reset');
  // });

  $(document).on("click", "#removeTest", function () {
    $(".modal-body #mainContent").text(selectedHomework.testName);
  });

  $("#formDeleteTestModal").submit(function (event) {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const id = selectedHomework.id;

    request = $.ajax({
      url: "/api/tests/" + id,
      type: "delete",
      contentType: "application/json; charset=utf-8",
      success: function (msg) {
        $inputs.prop("disabled", false);
        console.log(msg)
        window.location.reload();
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Xóa thất bại: \n", msg);
        window.location.reload();
      }
    });
  });

  $(document).on("click", "#btnSearchBaiTapLop", function (event) {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");
    $inputs.prop("disabled", true);

    // lấy từ khóa
    const keyword = $('#txtKeyword').val();
    // Xóa bảng
    $('#tblBaiTapLop tbody').empty();
    // Gửi request
    $.ajax({
      url: "/api/tests/search?query=" + keyword +"&type=HOMEWORK",
      type: "get",
      contentType: "application/json; charset=utf-8",
      success: function (matchedCourses) {
        $inputs.prop("disabled", false);
        // TODO: Chèn dl vào bảng
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Không tìm thấy: \n", msg);
      }
    });
  });
});
