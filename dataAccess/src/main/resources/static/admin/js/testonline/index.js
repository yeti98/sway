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


function renderTableBody(matchedTests){
  var tbody = "";
  matchedTests.forEach(function (test){
    tbody +=
        "<tr>\n" +
        "  <td class=\"truncatable\">\n" +
        "    <p>"+test.testId+"</p>\n" +
        "  </td>\n" +
        "  <td class=\"truncatable\">\n" +
        "    <p>"+test.testName+"</p>\n" +
        "  </td>\n" +
        "  <td class=\"truncatable\">\n" +
        "    <p>"+test.readableSubject+"</p>\n" +
        "  </td>" +
        "  <td class=\"truncatable\">\n" +
        "    <p>"+test.numberOfQuestion+"</p>\n" +
        "  </td>\n" +
        "  <td style=\"display: flex\">\n" +
        "    <div id=\"editAnUser\" data-homework="+test.jsonString +"\n" +
        "      onclick=\"javascript:setSelectedObject(this.getAttribute('data-homework'));\">\n" +
        "      <a class=\"edit\" href=\"manage/testonline/"+test.id+"\">\n" +
        "         <i class=\"material-icons\"\n" +
        "           data-toggle=\"tooltip\"\n" +
        "           title=\"Chỉnh sửa\">&#xE254;\n" +
        "         </i>\n" +
        "       </a>\n" +
        "     </div>\n" +
        "     <div id=\"removeTest\" data-homework="+test.jsonString +"\n" +
        "       onclick=\"javascript:setSelectedObject(this.getAttribute('data-homework'));\">\n" +
        "       <a class=\"delete\" data-toggle=\"modal\" href=\"#deleteTestModal\">\n" +
        "         <i class=\"material-icons\"\n" +
        "           data-toggle=\"tooltip\"\n" +
        "           title=\"Xóa\">&#xE872;\n" +
        "         </i>\n" +
        "       </a>\n" +
        "    </div>\n" +
        "  </td>\n" +
        "</tr>";
  });
  document.getElementById('mainTableBody').innerHTML = tbody;
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

  $(document).on("click", "#btnSearchTest", function (event) {
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
    $('#tblTestOnline tbody').empty();
    // Gửi request
    $.ajax({
      url: "/api/tests/search?query=" + keyword + "&type=TEST_ONLINE",
      type: "get",
      contentType: "application/json; charset=utf-8",
      success: function (matchedTests) {
        $inputs.prop("disabled", false);
        renderTableBody(matchedTests);
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Không tìm thấy: \n", msg);
      }
    });

  });
});
