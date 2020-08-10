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

    $(document).on("click", "#editAnUser", function () {
        // console.log(selectedUser);
        const id = selectedUser.id;
        const name = selectedUser.name;
        const username = selectedUser.username;
        const status = selectedUser.status;
        const role = selectedUser.role;

        $("#editName").val(name);
        $("#editEmail").val(username);
        $("#editIsLocked").val(status);
        $("#editRole").val(role);

    })

    $("#formEditUserModal").submit(function (event) {
        event.preventDefault();

        const $form = $(this);
        const $inputs = $form.find("input, select, button, textarea");

        const id = selectedUser.id;
        const name = $("#editName").val();
        const username = $("#editEmail").val();
        const avatar = selectedUser.avatar;
        const description = selectedUser.description;
        const type = selectedUser.type;
        const status = $("#editIsLocked").val();
        const role = $("#editRole").val();

        $inputs.prop("disabled", true);
        const editUser = JSON.stringify(
            {
                id, name, username,avatar,description,type,status,role
            }
        )

        console.log(editUser)

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
    })

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
})