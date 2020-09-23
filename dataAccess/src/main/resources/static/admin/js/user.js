
function onCopy() {
  const txtInfo = (document.getElementById('txtUserInfo'));
  $("#txtUserInfo").attr("disabled", false);
  txtInfo.select();
  document.execCommand('Copy');
  $("#txtUserInfo").attr("disabled", true);
  $('#copyTooltip').tooltip('show');
  setTimeout(function () {
    $('#copyTooltip').tooltip('hide')
  }, 2000)

}

function renderTableBody(matchedUsers){
  let tbody ="";
  matchedUsers.forEach(function (user){

    if (user.status == true) {
      user.status = "Hoạt động";
    }
    else{
      user.status = "Khoá";
    }
    user.jsonString = user.jsonString.replaceAll(' ','###DEV_CULI###');
    tbody +=
        " <tr>\n" +
        "   <td>\n" +
        "     <p>"+user.name+"</p>\n" +
        "   </td>\n" +
        "   <td>\n" +
        "     <p>"+user.username+"</p>\n" +
        "   </td>\n" +
        "   <td>\n" +
        "     <p>"+ user.status + "</p>\n" +
        "   </td>\n" +
        "   <td>\n" +
        "     <p>" + user.readableRole + "</p>\n" +
        "   </td> \n" +
        "   <td style=\"display: flex\">\n" +
        "     <div id=\"editAnUser\" " +
        "       onclick=\"javascript:setSelectedObject(this.getAttribute('data-user'));\"\n" +
        "       data-user="+user.jsonString+">\n" +
        "         <a class=\"edit\" data-toggle=\"modal\" href=\"#editUserModal\">\n" +
        "           <i class=\"material-icons\"" +
        "             data-toggle=\"tooltip\"" +
        "             title=\"Chỉnh sửa\">&#xE254;" +
        "           </i>\n" +
        "         </a>\n" +
        "     </div>\n" +
        "     <div id=\"removeAnUser\" onclick=\"javascript:setSelectedObject(this.getAttribute('data-user'));\"\n" +
        "       data-user=" + user.jsonString + ">\n" +
        "       <a class=\"delete\" data-toggle=\"modal\" href=\"#deleteUserModal\">\n" +
        "         <i class=\"material-icons\"" +
        "           data-toggle=\"tooltip\"" +
        "           title=\"Xóa\">&#xE872;" +
        "         </i>\n" +
        "       </a>\n" +
        "     </div>\n" +
        "   </td>\n" +
        "</tr>\n";
  });
  document.getElementById('tblbodyUser').innerHTML += tbody;
}

$(document).ready(function () {

  $('#copyTooltip').tooltip({trigger: 'click'});
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

  $(document).on("click", "#editAnUser", function () {
    //reset
    $("#editName").val('');
    $("#editEmail").val('');
    $("#editIsLocked").val(String('false'));
    $("#editRole").val('STUDENT');

    const id = selectedUser.id;
    const name = selectedUser.name.replaceAll('###DEV_CULI###',' ');
    const username = selectedUser.username;
    const status = selectedUser.status;
    const role = selectedUser.role;

    $("#editName").val(name);
    $("#editEmail").val(username);
    $("#editIsLocked").val(String(status));
    $("#editRole").val(role);

  });


  $(document).on("click", "#btnSearchUser", function (event) {
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
    $('#tblUser tbody').empty();
    // Gửi request
    $.ajax({
      url: "/api/users/search/byKey?query=" + keyword,
      type: "get",
      contentType: "application/json; charset=utf-8",
      success: function (matchedUsers) {
        $inputs.prop("disabled", false);
        renderTableBody(matchedUsers);
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Không tìm thấy: \n", msg);
      }
    });
  });


  $('#addUsername').on('input', function () {
    const username = $("#addUsername").val();
    const password = $("#addPassword").val();
    $('#txtUserInfo').val(username + '\n' + password);
  });

  $(document).on("click", "#addUser", function () {


    $.ajax({
      url: "/admin/randomPass",
      type: "get",
      contentType: "application/json; charset=utf-8",
      success: function (res) {
        const obj = JSON.parse(res);
        console.log("random password", obj.password);
        $("#addPassword").val(obj.password);
      },
      error: function (msg) {
        alert("Có lỗi xảy ra!");
      }
    });


  });
  $("#formAddUserModal").submit(function (event) {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const name = "";
    const username = $("#addUsername").val();
    const password = $("#addPassword").val();
    const role = $("#addRole").val();
    const status = true;

    $inputs.prop("disabled", true);
    const addUser = JSON.stringify({
      name,username, password, role, status
    });

    console.log(addUser);
    $.ajax({
      url: "/admin/users/",
      type: "post",
      data: addUser,
      contentType: "application/json; charset=utf-8",
      success: function (msg) {
        $inputs.prop("disabled", false);
        window.location.reload();
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Username đã tồn tại");
      }
    });
  });

  $("#formEditUserModal").submit(function (event) {
    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const id = selectedUser.id;
    const name = $("#editName").val().replaceAll('###DEV_CULI###',' ');
    const username = $("#editEmail").val();
    const avatar = selectedUser.avatar;
    const description = selectedUser.description;
    const type = selectedUser.type;
    const status = $("#editIsLocked").val();
    const role = $("#editRole").val();

    $inputs.prop("disabled", true);
    const editUser = JSON.stringify(
      {
        id, name, username, avatar, description, type, status, role
      }
    );

    console.log(editUser);

    $.ajax({
      url: "/admin/users/" + id,
      type: "put",
      data: editUser,
      contentType: "application/json; charset=utf-8",
      success: function (msg) {
        $inputs.prop("disabled", false);
        window.location.reload();
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Sửa thất bại: \n", msg);
      }
    });
  });

  $("#formDeleteUserModal").submit(function (event) {

    event.preventDefault();

    const $form = $(this);
    const $inputs = $form.find("input, select, button, textarea");

    const UserId = selectedUser.id;

    request = $.ajax({
      url: "/admin/users/" + UserId,
      type: "delete",
      contentType: "application/json; charset=utf-8",
      success: function (msg) {
        $inputs.prop("disabled", false);
        window.location.reload();
      },
      error: function (msg) {
        $inputs.prop("disabled", false);
        alert("Xóa thất bại: \n", msg);
        window.location.reload();
      }
    });
  })
});
