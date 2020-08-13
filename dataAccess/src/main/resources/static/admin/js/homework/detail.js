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

function getTableBody() {
  return document.getElementById("tblQuestionList").getElementsByTagName("tbody")[0];
}

function deleteRow(index, question) {
  console.log(index, question);
  swayTest.questions.splice(index, 1);
  const tblQuestionList = getTableBody();
  tblQuestionList.deleteRow(index)
  rerenderTable()
}

function rerenderTable() {
  const tblQuestionList = document.getElementById("tblQuestionList").getElementsByTagName("tbody")[0];
  $("#tblQuestionList tbody").empty()
  swayTest.questions.forEach((question, index) => {
    var row = tblQuestionList.insertRow(index);
    var stt = row.insertCell(0);
    var macauhoi = row.insertCell(1);
    var noidung = row.insertCell(2);
    var capnhat = row.insertCell(3);
    stt.innerHTML = `<a href=\"manage/questions/${question.id}"><span>${index + 1}</span></a>`;
    macauhoi.innerHTML = question.questionId;
    noidung.innerHTML = question.content;
    capnhat.innerHTML = "<div style='display: flex'>" +
      "             <div id=\"editAnUser\" " + `onclick="deleteRow(${index}, ${question.id})" >` +
      "                <a class=\"edit\" " + `href="manage/questions/${question.id}"` + ">\n" +
      "                  <i class=\"material-icons\"\n" +
      "                     data-toggle=\"tooltip\"\n" +
      "                     title=\"Chỉnh sửa câu hỏi\">&#xE254;\n" +
      "                  </i>\n" +
      "                </a>\n" +
      "              </div>" +
      "              <div id=\"editAnUser\" " + `onclick="deleteRow(${index}, ${question.id})" >` +
      "                <a class=\"delete\" data-toggle=\"modal\" href=\"#deleteUserModal\">\n" +
      "                  <i class=\"material-icons\"\n" +
      "                     data-toggle=\"tooltip\"\n" +
      "                     title=\"Xóa khỏi bài tập này\">&#xE872;\n" +
      "                  </i>\n" +
      "                </a>\n" +
      "              </div>\n" +
      "</div>"
  });
}

$(document).ready(function () {
  // Activate tooltip
  $('[data-toggle="tooltip"]').tooltip();

  $(document).on("click", "#btnAddQuestion", function () {
    $("#formAddUserModal").trigger('reset');
  });

  $("#swayTestForm").submit(function (event) {
    console.log("SUBMIT");
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const id = swayTest.id;
    swayTest.testId = $("#testId").val();
    swayTest.testName = $("#testName").val();


    $inputs.prop("disabled", true);
    const payload = JSON.stringify(swayTest);
    $.ajax({
      url: "/api/tests/" + swayTest.id,
      type: "put",
      data: payload,
      contentType: "application/json; charset=utf-8",
      success: function (msg) {
        $inputs.prop("disabled", false);
        window.history.back();
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Thêm thất bại: \n", msg);
      }
    });
  });

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

  $("#importData").submit((event) => {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");
    $inputs.prop("disabled", true);

    var form_data = new FormData();
    const attachment_data= $("#importData").find("input")[0].files[0];
    form_data.append("file", attachment_data);

    $.ajax({
      type: "POST",
      data: form_data,
      url: "/files/upload?operation=1&target="+swayTest.id,
      contentType: false,
      processData: false,
      success: function (data) {
        $inputs.prop("disabled", false);
        $('#importData').trigger('reset');
        swayTest.questions.push(...data)
        rerenderTable()
      },
      error: function (data) {
        alert("Lỗi xử lý")
      }
    });
  });

  $("#formAddQuestionModal").submit(function (event) {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const questionId = $("#addQuestionID").val();
    const content = $("#addQuestionContent").val();
    const explanation = $("#addQuestionExpl").val();

    const A = $("#addChoiceA").val().trim();
    const B = $("#addChoiceB").val().trim();
    const C = $("#addChoiceC").val().trim();
    const D = $("#addChoiceD").val().trim();
    const E = $("#addChoiceE").val().trim();
    let answer;
    // TODO: Sửa lại các lấy input checked jquery
    // TODO: Check đáp án trùng nhau
    if (document.getElementById("radioBtnA").checked && !isBlank(A)) {
      answer = A;
    } else if (document.getElementById("radioBtnB").checked && !isBlank(B)) {
      answer = B
    } else if (document.getElementById("radioBtnC").checked && !isBlank(C)) {
      answer = C;
    } else if (document.getElementById("radioBtnD").checked && !isBlank(D)) {
      answer = D;
    } else if (document.getElementById("radioBtnE").checked && !isBlank(E)) {
      answer = E;
    }
    const choices = [];
    if (isAllBlank(A, B, C, D, E) || isBlank(answer)) {
      alert("Vui lòng điền thông tin đáp án");
    } else {
      if (!isBlank(A)) choices.push(A);
      if (!isBlank(B)) choices.push(B);
      if (!isBlank(C)) choices.push(C);
      if (!isBlank(D)) choices.push(D);
      if (!isBlank(E)) choices.push(E);

      $inputs.prop("disabled", true);

      const payload = JSON.stringify(
        {
          choices, answer, explanation, active: true, questionId, content
        }
      );
      console.log(payload);
      $.ajax({
        url: "/api/questions",
        type: "post",
        data: payload,
        contentType: "application/json; charset=utf-8",
        success: function (msg) {
          $inputs.prop("disabled", false);
          swayTest.questions.push(msg)
          rerenderTable()
          $("#addQuestionModal").modal("hide");
        },
        error: function (msg) {
          $inputs.prop("disabled", false);
          alert("Thêm thất bại: \n", msg);
          $("#addQuestionModal").modal("hide");
        }
      });
    }
  });
});
