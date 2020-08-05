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

  // $(document).on("click", "#removeAnUser", function () {
  //   const content = $(this).data('content');
  //   $(".modal-body #mainContent").text(content);
  // });

  $(document).on("click", "#editAnUser", function () {
    console.log(selectedQuestion);
    const id = selectedQuestion.id;
    const questionId = selectedQuestion.questionId;
    const content = selectedQuestion.content;
    const choices = selectedQuestion.choices.split("###DEVCULI###");
    const answer = selectedQuestion.answer;
    const explanation = selectedQuestion.explanation;
    const active = selectedQuestion.active;


    $("#editQuestionID").val(questionId);
    $("#editQuestionContent").val(content);
    $("#editQuestionExpl").val(explanation);
    $("#editChoiceA").val(choices[0]);
    $("#editRadioBtnA").prop("checked", true);
    if (choices.length > 1) {
      $("#editChoiceB").val(choices[1]);
      if (answer === choices[1]) {
        $("#editRadioBtnB").prop("checked", true);
      }
    }
    if (choices.length > 2) {
      $("#editChoiceC").val(choices[2]);
      if (answer === choices[2]) {
        $("#editRadioBtnC").prop("checked", true);
      }
    }
    if (choices.length > 3) {
      $("#editChoiceD").val(choices[3]);
      if (answer === choices[3]) {
        $("#editRadioBtnD").prop("checked", true);
      }
    }
    if (choices.length > 4) {
      $("#editChoiceE").val(choices[4]);
      if (answer === choices[4]) {
        $("#editRadioBtnE").prop("checked", true);
      }
    }
  });

  $("#formEditUserModal").submit(function (event) {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const id = selectedQuestion.id;
    if (id == null) return;
    const questionId = $("#editQuestionID").val();
    const content = $("#editQuestionContent").val();
    const explanation = $("#editQuestionExpl").val();

    const A = $("#editChoiceA").val().trim();
    const B = $("#editChoiceB").val().trim();
    const C = $("#editChoiceC").val().trim();
    const D = $("#editChoiceD").val().trim();
    const E = $("#editChoiceE").val().trim();
    let answer;
    // TODO: Sửa lại các lấy input checked jquery
    if (document.getElementById("editRadioBtnA").checked && !isBlank(A)) {
      answer = A;
    } else if (document.getElementById("editRadioBtnB").checked && !isBlank(B)) {
      answer = B
    } else if (document.getElementById("editRadioBtnC").checked && !isBlank(C)) {
      answer = C;
    } else if (document.getElementById("editRadioBtnD").checked && !isBlank(D)) {
      answer = D;
    } else if (document.getElementById("editRadioBtnE").checked && !isBlank(E)) {
      answer = E;
    }
    const choices = [];
    if (isAllBlank(A, B, C, D) || isBlank(answer)) {
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
      console.log("PUT", payload);
      $.ajax({
        url: "/admin/manage/questions/" + id,
        type: "put",
        data: payload,
        contentType: "application/json; charset=utf-8",
        success: function (msg) {
          $inputs.prop("disabled", false);
          window.location.reload();
        },
        error: function (msg) {
          $inputs.prop("disabled", false);
          alert("Thêm thất bại: \n", msg);
        }
      });
    }
  });

  $("#formAddUserModal").submit(function (event) {
    console.log("SUBMIT");
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
    if (isAllBlank(A, B, C, D) || isBlank(answer)) {
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
        url: "/admin/manage/questions",
        type: "post",
        data: payload,
        contentType: "application/json; charset=utf-8",
        success: function (msg) {
          $inputs.prop("disabled", false);
          window.location.reload();
        },
        error: function (msg) {
          $inputs.prop("disabled", false);
          alert("Thêm thất bại: \n", msg);
        }
      });
    }
  });

  // $("#formDeleteUserModal").submit(function (event) {
  //   event.preventDefault();
  //
  //   const $form = $(this);
  //   const $inputs = $form.find("input, select, button, textarea");
  //
  //   const mainContent = $(".modal-body #mainContent").text();
  //   const email = mainContent.split("- ")[1].trim();
  //
  //   request = $.ajax({
  //     url: "/users/" + email,
  //     type: "delete",
  //     contentType: "application/json; charset=utf-8",
  //     success: function (msg) {
  //       $inputs.prop("disabled", false);
  //       window.location.reload();
  //     },
  //     error: function (msg) {
  //       $inputs.prop("disabled", false);
  //       alert("Xóa thất bại: \n", msg);
  //       window.location.reload();
  //     }
  //   });
  // })
});
