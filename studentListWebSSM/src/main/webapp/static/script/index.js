window.onload = function () {
    // function showStudentList() {
    //     $.getJSON("http://localhost:8080/studentListWeb/client/bookServlet",
    //         "action=list",
    //         function (jsonData) {
    //             console.log('aa');
    //             console.log(jsonData);
    //             alert(jsonData);
    //
    //             new Vue({
    //                 el: "#students_tbody",
    //                 data: {
    //                     studentInfo: jsonData
    //                 }
    //             })
    //         }
    //     );
    // }

    // showStudentList();

    // let inputMinAge = document.getElementById('min_age');
    // if (inputMinAge.value === 0) {
    //     inputMinAge.value = '';
    // }
    //
    // const MAX_INTEGER = 2147483647;
    // let inputMaxAge = document.getElementById('max_age');
    // if (inputMaxAge.value === MAX_INTEGER) {
    //     inputMaxAge.value = '';
    // }

    // 对删除进行二次确认
    const aDeleteItems = document.getElementsByClassName('a_delete_item');
    // for (let i = 0; i < aDeleteItems.length; i++) {
    //     let aDeleteItem = aDeleteItems[i];
    //     aDeleteItem.onclick = function () {
    //         return confirm(`是否确认删除该生信息？`);
    //     }
    // }

    // 点击“注销”按钮时二次确认
    document.getElementById('a_logout').onclick = function () {
        return confirm(`是否确认注销账户？`);
    }


    // 处理删除链接
    // 使用了Vue
    let deleteVue = new Vue({
        el: '#students_table',
        methods: {
            deleteStudent: function (event) {
                if (confirm('是否确认删除?')) {
                    // 通过id获取表单标签
                    let deleteForm = document.getElementById('delete_form');
                    // 将触发事件的超链接的href属性为表单的action属性赋值
                    deleteForm.action = event.target.href;
                    // 提交表单
                    deleteForm.submit();
                    // 阻止超链接的默认跳转行为
                    event.preventDefault();
                } else {
                    // 取消删除
                    // 阻止超链接的默认跳转行为
                    event.preventDefault();
                }
            }
        }
    });

    // 提交model中的表单
    document.getElementById('btn-save-add-student-form').onclick = function () {
        // let formAddStudentUrl = document.getElementById('form-add-student-url').innerText;

        // 获取表单的action, 作为ajax提交的url
        // let formAddStudentAction = document.getElementById('form-add-student').action;
        // console.log(formAddStudentAction);

        // $.ajax({
        //     url: formAddStudentAction,
        //     type: 'PUT',
        //     data: $('#model-add-student form').serialize(),
        //     success: function () {
        //         $('#model-add-student').modal('hide');
        //     }
        // });

        // 直接提交，不用ajax提交了
        // 用ajax提交表单的写法会有不明原因的bug, 400 Bad Request, 原因未知
        $('#form-add-student').submit();
    }


    // checkbox
    let checkboxes = document.getElementsByName('checkboxStuId');
    for (let i = 0; i < checkboxes.length; i++) {
        checkboxes[i].onclick = function () {

        }
    }

    document.getElementById('btn-delete-chosen-students').onclick = function () {
        if (confirm('是否删除所有选择的内容?')) {
            // 用户确认删除
            let ids = '';
            let count = 0;

            for (let i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].checked) {
                    // 某个学生的复选框被选中

                    let id = checkboxes[i].value;

                    if (count === 0) {
                        ids += 'stuId=' + id;
                    } else {
                        ids += '&stuId=' + id;
                    }
                    // ids.push(id);

                    ids[count] = id;

                    count++;
                }
            }

            // 上面会拼接出一个名为stuId的数组stuId=1&stuId=2&stuId=3&stuId=4……
            $.get("deleteChosenStudents", ids, function (data) {
                if (data === 'ok') {
                    alert('删除成功！');
                    // 删除成功后，调用action方法刷新页面信息
                    location.reload();
                } else {
                    alert('删除失败！');
                }
            });

            // $.ajax({
            //     url: 'deleteChosenStudents',
            //     type: 'DELETE',
            //     contentType: 'application/json',
            //     dataType: 'json',
            //     data: ids,
            //     success: function () {
            //         location.reload();
            //     }
            //
            // });

        }
    }
}