function isEmpty(str) {
  return (!str || 0 === str.length);
}

function getTableBody() {
  return document.getElementById("tblTestList").getElementsByTagName("tbody")[0];
}

function deleteRow(index, question) {
  lesson.tests.splice(index, 1);
  const tblQuestionList = getTableBody();
  tblQuestionList.deleteRow(index);
  rerenderTable()
}

function rerenderTable() {
  const tblTestList = document.getElementById("tblTestList").getElementsByTagName("tbody")[0];
  $("#tblTestList tbody").empty();
  lesson.tests.forEach((test, index) => {
    var row = tblTestList.insertRow(index);
    var stt = row.insertCell(0);
    var mabaitest = row.insertCell(1);
    var tenbaitest = row.insertCell(2);
    var socauhoi = row.insertCell(3);
    var capnhat = row.insertCell(4);
    stt.innerHTML = `<a href=\"manage/tests/${test.id}"><span>${index + 1}</span></a>`;
    mabaitest.innerHTML = test.testId;
    tenbaitest.innerHTML = test.testName;
    socauhoi.innerHTML = test.numberOfQuestion;
    capnhat.innerHTML = "<div style='display: flex'>" +
      "             <div id=\"editAnUser\" " + `onclick="deleteRow(${index}, ${test.id})" >` +
      "                <a class=\"edit\" " + `href="manage/tests/${test.id}"` + ">\n" +
      "                  <i class=\"material-icons\"\n" +
      "                     data-toggle=\"tooltip\"\n" +
      "                     title=\"Chỉnh sửa bài tập\" style=\"color: orange\">&#xE254;\n" +
      "                  </i>\n" +
      "                </a>\n" +
      "              </div>" +
      "              <div id=\"editAnUser\" " + `onclick="deleteRow(${index}, ${test.id})" >` +
      "                <a class=\"delete\" data-toggle=\"modal\" href=\"#deleteUserModal\">\n" +
      "                  <i class=\"material-icons\"\n" +
      "                     data-toggle=\"tooltip\"\n" +
      "                     title=\"Xóa khỏi bài học này\" style=\"color: red\">&#xE872;\n" +
      "                  </i>\n" +
      "                </a>\n" +
      "              </div>\n" +
      "</div>"
  });
}


function insertOption(swayTest) {
  $('#slTestResult').append(`
    <option value="${swayTest.id}">${swayTest.testId} - ${swayTest.testName} </option>`);
}

function isExisted(addTest) {
  console.log(lesson.tests.length)
  for (let i = 0; i < lesson.tests.length; i++) {
    if (lesson.tests[i].id === addTest.id) {
      return true;
    }
  }
  return false;
}

$(document).ready(function () {
  // Activate tooltip
  $('[data-toggle="tooltip"]').tooltip();

  $(document).on("click", "#btnAddTest", function () {
    $("#formAddTest").trigger('reset');
    $('#slTestResult').empty()
  });

  $(document).on("click", "#addTestResult", function () {
    const testId = $("#slTestResult").children("option:selected").val();
    var addTest = JSON.parse(window.localStorage.getItem("result-test" + testId));
    if (!isExisted(addTest)) {
      lesson.tests.push(addTest);
      rerenderTable();
    }
    $('#findTestModal').modal('hide')
  });

  $("#lessonForm").submit(function (event) {
    console.log("submit");
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const id = lesson.id;
    lesson.lessonId = $("#lessonId").val();
    lesson.name = $("#lessonName").val();

    $inputs.prop("disabled", true);
    const payload = JSON.stringify(lesson);
    console.log(payload);
    $.ajax({
      url: "/api/lessons/" + lesson.id,
      type: "put",
      data: payload,
      contentType: "application/json; charset=utf-8",
      success: function (msg) {
        $inputs.prop("disabled", false);
        window.history.back();
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Lưu thất bại: \n", msg);
      }
    });
  });

  $("#formAddTest").submit((event) => {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");
    $inputs.prop("disabled", true);

    const keyword = $('#txtKeyword').val();
    $('#slTestResult').empty()
    $.ajax({
      url: "/api/tests/search?query=" + keyword +"&type=homework",
      type: "get",
      contentType: "application/json; charset=utf-8",
      success: function (matchedTests) {
        $inputs.prop("disabled", false);
        matchedTests.forEach((test) => {
          window.localStorage.setItem("result-test" + test.id, JSON.stringify(test));
          insertOption(test)
        });
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Không tìm thấy: \n", msg);
        $("#addQuestionModal").modal("hide");
      }
    });
  });

  // $("#formAddTest").submit(function (event) {
  //   event.preventDefault();
  //
  //   const $form = $(this);
  //   const $inputs = $form.find("input, select, button, textarea");
  //
  //   const questionId = $("#addQuestionID").val();
  //   const content = $("#addQuestionContent").val();
  //   const explanation = $("#addQuestionExpl").val();
  //
  //   const A = $("#addChoiceA").val().trim();
  //   const B = $("#addChoiceB").val().trim();
  //   const C = $("#addChoiceC").val().trim();
  //   const D = $("#addChoiceD").val().trim();
  //   const E = $("#addChoiceE").val().trim();
  //   let answer;
  //   // TODO: Sửa lại các lấy input checked jquery
  //   // TODO: Check đáp án trùng nhau
  //   if (document.getElementById("radioBtnA").checked && !isBlank(A)) {
  //     answer = A;
  //   } else if (document.getElementById("radioBtnB").checked && !isBlank(B)) {
  //     answer = B
  //   } else if (document.getElementById("radioBtnC").checked && !isBlank(C)) {
  //     answer = C;
  //   } else if (document.getElementById("radioBtnD").checked && !isBlank(D)) {
  //     answer = D;
  //   } else if (document.getElementById("radioBtnE").checked && !isBlank(E)) {
  //     answer = E;
  //   }
  //   const choices = [];
  //   if (isAllBlank(A, B, C, D, E) || isBlank(answer)) {
  //     alert("Vui lòng điền thông tin đáp án");
  //   } else {
  //     if (!isBlank(A)) choices.push(A);
  //     if (!isBlank(B)) choices.push(B);
  //     if (!isBlank(C)) choices.push(C);
  //     if (!isBlank(D)) choices.push(D);
  //     if (!isBlank(E)) choices.push(E);
  //
  //     $inputs.prop("disabled", true);
  //
  //     const payload = JSON.stringify(
  //       {
  //         choices, answer, explanation, active: true, questionId, content
  //       }
  //     );
  //     console.log(payload);
  //     $.ajax({
  //       url: "/api/questions",
  //       type: "post",
  //       data: payload,
  //       contentType: "application/json; charset=utf-8",
  //       success: function (msg) {
  //         $inputs.prop("disabled", false);
  //         lesson.questions.push(msg);
  //         rerenderTable();
  //         $("#addQuestionModal").modal("hide");
  //       },
  //       error: function (msg) {
  //         $inputs.prop("disabled", false);
  //         alert("Thêm thất bại: \n", msg);
  //         $("#addQuestionModal").modal("hide");
  //       }
  //     });
  //   }
  // });
});
