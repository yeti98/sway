function isEmpty(str) {
  return (!str || 0 === str.length);
}

function getTableBody() {
  return document.getElementById("tblTestList").getElementsByTagName("tbody")[0];
}

function deleteRow(index, question) {
  course.lessons.splice(index, 1);
  const tblQuestionList = getTableBody();
  tblQuestionList.deleteRow(index);
  rerenderTable()
}

function rerenderTable() {
  const tblTestList = document.getElementById("tblTestList").getElementsByTagName("tbody")[0];
  $("#tblTestList tbody").empty();
  course.lessons.forEach((lesson, index) => {
    var row = tblTestList.insertRow(index);
    var stt = row.insertCell(0);
    var mabaihoc = row.insertCell(1);
    var tenbaihoc = row.insertCell(2);
    var sobaitest = row.insertCell(3);
    var capnhat = row.insertCell(4);
    stt.innerHTML = `<a href=\"manage/tests/${lesson.id}"><span>${index + 1}</span></a>`;
    mabaihoc.innerHTML = lesson.lessonId;
    tenbaihoc.innerHTML = lesson.name;
    sobaitest.innerHTML = lesson.numberOfTest;
    capnhat.innerHTML = "<div style='display: flex'>" +
      "             <div id=\"editAnUser\" " + `onclick="deleteRow(${index}, ${lesson.id})" >` +
      "                <a class=\"edit\" " + `href="manage/lessons/${lesson.id}"` + ">\n" +
      "                  <i class=\"material-icons\"\n" +
      "                     data-toggle=\"tooltip\"\n" +
      "                     title=\"Chỉnh sửa bài tập\" style=\"color: orange\">&#xE254;\n" +
      "                  </i>\n" +
      "                </a>\n" +
      "              </div>" +
      "              <div id=\"editAnUser\" " + `onclick="deleteRow(${index}, ${lesson.id})" >` +
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


function insertOption(lesson) {
  $('#slLessonResult').append(`
    <option value="${lesson.id}">${lesson.lessonId} - ${lesson.name} </option>`);
}

function isExisted(addTest) {
  console.log(course.lessons.length)
  for (let i = 0; i < course.lessons.length; i++) {
    if (course.lessons[i].id === addTest.id) {
      return true;
    }
  }
  return false;
}

$(document).ready(function () {
  // Activate tooltip
  $('[data-toggle="tooltip"]').tooltip();

  $(document).on("click", "#btnAddLesson", function () {
    $("#formAddCourse").trigger('reset');
    $('#slLessonResult').empty()
  });

  $(document).on("click", "#addLessonResult", function () {
    const testId = $("#slLessonResult").children("option:selected").val();
    var addLesson = JSON.parse(window.localStorage.getItem("result-lesson" + testId));
    if (!isExisted(addLesson)) {
      course.lessons.push(addLesson);
      rerenderTable();
    }
    $('#findLessonModal').modal('hide')
  });

  $("#courseForm").submit(function (event) {
    console.log("submit");
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const id = course.id;
    course.courseId = $("#courseId").val();
    course.name = $("#courseName").val();

    $inputs.prop("disabled", true);
    const payload = JSON.stringify(course);
    console.log(payload);
    $.ajax({
      url: "/api/courses/" + course.id,
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

  $("#formAddCourse").submit((event) => {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");
    $inputs.prop("disabled", true);

    const keyword = $('#txtKeyword').val();
    $('#slLessonResult').empty()
    $.ajax({
      url: "/api/lessons/search?query=" + keyword,
      type: "get",
      contentType: "application/json; charset=utf-8",
      success: function (matchedLessons) {
        $inputs.prop("disabled", false);
        matchedLessons.forEach((test) => {
          window.localStorage.setItem("result-lesson" + test.id, JSON.stringify(test));
          insertOption(test)
        });
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Thêm thất bại: \n", msg);
        $("#addQuestionModal").modal("hide");
      }
    });
  });

  // $("#formAddCourse").submit(function (event) {
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
  //         course.questions.push(msg);
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
