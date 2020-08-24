function isEmpty(str) {
  return (!str || 0 === str.length);
}

function getTableBody() {
  return document.getElementById("tblUserList").getElementsByTagName("tbody")[0];
}

function deleteRow(index, question) {
  swayClass.students.splice(index, 1);
  const tblUserList = getTableBody();
  tblUserList.deleteRow(index);
  rerenderTable()
}

function rerenderTable() {
  const tblUserList = document.getElementById("tblUserList").getElementsByTagName("tbody")[0];
  $("#tblUserList tbody").empty();
  swayClass.students.forEach((student, index) => {
    var row = tblUserList.insertRow(index);
    var stt = row.insertCell(0);
    var tenhocvien = row.insertCell(1);
    var username = row.insertCell(2);
    var capnhat = row.insertCell(3);
    stt.innerHTML = `<span>${index + 1}</span>`;
    tenhocvien.innerHTML = student.name;
    username.innerHTML = student.username;
    capnhat.innerHTML =
      "<div style='display: ruby'>" +
      "   <div id=\"removeUser\" " + `onclick="deleteRow(${index}, ${student.id})" >` +
      "      <a class=\"delete\" data-toggle=\"modal\" href=\"#deleteUserModal\">\n" +
      "         <i class=\"material-icons\"\n" +
      "            data-toggle=\"tooltip\"\n" +
      "            title=\"Xóa khỏi lớp học này\" style=\"color: red\">&#xE872;\n" +
      "         </i>\n" +
      "      </a>\n" +
      "   </div>\n" +
      "</div>"
  });
}

function renderCourseTable() {
  const tblCourseInfo = document.getElementById("tblCourseInfo").getElementsByTagName("tbody")[0];
  $("#tblCourseInfo tbody").empty();
  var row = tblCourseInfo.insertRow(0);
  var courseId = row.insertCell(0);
  var courseName = row.insertCell(1);
  var numberOfLesson = row.insertCell(2);
  var action = row.insertCell(3);
  courseId.innerHTML = swayClass.course.courseId;
  courseName.innerHTML = swayClass.course.name;
  numberOfLesson.innerHTML = swayClass.course.numberOfLesson;
  action.innerHTML = "<div style=\"display: ruby\">\n" +
    "                            <div id=\"editLesson\">\n" +
    "                              <a class=\"edit\"" + `href=\"'manage/courses/'+${swayClass.course.id}` + "\">\n" +
    "                                <i class=\"material-icons\"\n" +
    "                                   data-toggle=\"tooltip\"\n" +
    "                                   style=\"color: orange\" title=\"Chỉnh sửa\">&#xE254;\n" +
    "                                </i>\n" +
    "                              </a>\n" +
    "                            </div>\n" +
    "                            <div id=\"removeLesson\" onclick=\"removeCourse();\">\n" +
    "                              <a class=\"delete\" data-toggle=\"modal\" href=\"#deleteLessonModal\">\n" +
    "                                <i class=\"material-icons\"\n" +
    "                                   data-toggle=\"tooltip\" style=\"color: red\"\n" +
    "                                   title=\"Xóa\">&#xE872;\n" +
    "                                </i>\n" +
    "                              </a>\n" +
    "                            </div>\n" +
    "                          </div>"
}


function insertOption(course) {
  $('#slCourseResult').append(`
    <option value="${course.id}">${course.courseId} - ${course.name} </option>`);
}

function isExisted(obj, arr) {
  for (let i = 0; i < arr.length; i++) {
    if (arr[i].id === obj.id) {
      return true;
    }
  }
  return false;
}


$(document).ready(function () {
  // Activate tooltip
  $('[data-toggle="tooltip"]').tooltip();

  $(document).on("click", "#btnAddCourse", function () {
    $("#formAddCourse").trigger('reset');
    $('#slCourseResult').empty()
  });

  $(document).on("click", "#addCourseResult", function () {
    const testId = $("#slCourseResult").children("option:selected").val();
    var course = JSON.parse(window.localStorage.getItem("result-course" + testId));
    swayClass.course = course;
    renderCourseTable();
    $("#btnAddCourse").hide();
    $('#findCourseModal').modal('hide')
  });

  $("#formAddUser").submit(function (event) {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const username = $("#txtAddStudent").val();

    $inputs.prop("disabled", true);
    $.ajax({
      url: "/api/users/search?username=" + username,
      type: "get",
      contentType: "application/json; charset=utf-8",
      success: function (msg) {
        $inputs.prop("disabled", false);
        console.log(msg);
        if (msg.length === 0) {
          $('#searchNotify').show();
          setTimeout(function () {
            $('#searchNotify').hide()
          }, 2000)
        } else {
          const user = msg;
          if (!isExisted(user, swayClass.students)) {
            swayClass.students.push(user);
            rerenderTable()
          }
          $('#findUserModal').modal('hide');
        }
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Không tìm thấy: " + username);
      }
    });
  });

  $("#swayClassForm").submit(function (event) {
    console.log("submit");
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const id = swayClass.id;
    swayClass.classId = $("#swayClassId").val();
    swayClass.name = $("#swayClassName").val();
    swayClass.minScore = $("#swayClassMinScore").val();

    $inputs.prop("disabled", true);
    const payload = JSON.stringify(swayClass);
    console.log(payload);
    $.ajax({
      url: "/api/classes/" + swayClass.id,
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
    $('#slCourseResult').empty();
    $.ajax({
      url: "/api/courses/search?query=" + keyword,
      type: "get",
      contentType: "application/json; charset=utf-8",
      success: function (matchedCourses) {
        $inputs.prop("disabled", false);
        matchedCourses.forEach((test) => {
          window.localStorage.setItem("result-course" + test.id, JSON.stringify(test));
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

  // $("#formAddswayClass").submit(function (event) {
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
  //         swayClass.questions.push(msg);
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
