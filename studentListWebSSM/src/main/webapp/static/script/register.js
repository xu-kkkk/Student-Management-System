window.onload = function () {
    // 在html中就将其隐藏。否则页面刚进入的时候alert会一闪而过，因为先此处的js代码是在页面加载完成后才执行
    // document.getElementById('alert_message').style.display = 'none';

    // 校验用户输入的两遍密码是否正确
    document.getElementById('input_confirm_password').onblur = function () {
        let password = document.getElementById('input_password').value;
        let confirmPassword = document.getElementById('input_confirm_password').value;

        if (password !== confirmPassword) {
            document.getElementById('alert_message').innerHTML = `<span class="">两次输入的密码不正确！</span>`;
            // document.getElementById('alert_message').style.display = 'block';

            // 若两遍密码不匹配，则禁用提交按钮
            // document.getElementById('btn_submit').disabled = true;

            showAlertAndDisableBtn();
        } else {
            // 两遍密码相同，恢复提交按钮，允许用户提交表单
            hideAlertAndResumeBtn()
        }
    }

    // 判断用户输入的用户名是否已经存在。若存在，使用ajax的方式将警告写进alert中
    document.getElementById('input_username').onblur = function () {
        // 获取用户输入的用户名
        let username = document.getElementById('input_username').value;

        // 向Servlet发出请求，并处理服务器返回的数据
        $.getJSON(
            `http://localhost:8080/studentListWeb/userServlet`,
            `action=ajaxExistUsername&username=${username}`,
            function (data) {
                // data是从Servlet服务器传来的json，这里作为json对象使用

                if (data.existUsername === true) {
                    // 用户名已存在
                    document.getElementById('alert_message').innerHTML = `用户名已存在！`;
                    showAlertAndDisableBtn();
                } else {
                    // 用户名不存在（用户名合法）
                    // 恢复提交按钮，允许用户提交表单
                    hideAlertAndResumeBtn();
                }
            }
        );
    }

    // 隐藏alert，恢复提交按钮
    function hideAlertAndResumeBtn() {
        document.getElementById('alert_message').style.display = 'none';
        document.getElementById('btn_submit').disabled = false;
    }

    // 显示alert，禁用提交按钮
    function showAlertAndDisableBtn() {
        document.getElementById('alert_message').style.display = 'block';
        document.getElementById('btn_submit').disabled = true;
    }
}