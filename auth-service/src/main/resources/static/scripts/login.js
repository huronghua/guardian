$(function () {
    var usernameInput = document.querySelector(".usernameInput");
    var usernameWrap=document.querySelector(".username-wrap");
    var passwordInput=document.querySelector(".passwordInput");
    var pwdWrap=document.querySelector(".pwd-wrap");
    var yonghuming=document.querySelector(".icon-yonghuming");
    var usernameBorder = document.querySelector(".usernameBorder");
    var pwdBorder = document.querySelector(".pwdBorder");
    var lock=document.querySelector(".icon-lock");
    var loginBtn=document.querySelector(".loginBtn");
    var notice=document.querySelector(".notice");

    function message(msg){
        notice.innerHTML=msg;
        notice.style.display="block";
    };

    usernameInput.addEventListener("focus", function () {
        this.parentNode.classList.add("removeBorder");
        usernameBorder.classList.add("focus");
        yonghuming.classList.add("active");
        notice.style.display="none";
    });
    usernameInput.addEventListener("blur", function () {
        if(!usernameInput.value.trim()){
            this.parentNode.classList.remove("removeBorder");
            usernameBorder.classList.remove("focus");
            yonghuming.classList.remove("active");
        }
    });
    usernameWrap.addEventListener("mouseover", function () {
        this.classList.add("removeBorder");
        usernameBorder.classList.add("focus");
        yonghuming.classList.add("active");
    });
    usernameWrap.addEventListener("mouseout", function () {
        if(!usernameInput.value.trim()){
            this.classList.remove("removeBorder");
            usernameBorder.classList.remove("focus");
            yonghuming.classList.remove("active");
        }
    });
    passwordInput.addEventListener("focus", function () {
        this.parentNode.classList.add("removeBorder");
        pwdBorder.classList.add("focus");
        lock.classList.add("active");
        notice.style.display="none";
    });
    passwordInput.addEventListener("blur", function () {
        if(!passwordInput.value.trim()){
            this.parentNode.classList.remove("removeBorder");
            pwdBorder.classList.remove("focus");
            lock.classList.remove("active");
        }
    });
    pwdWrap.addEventListener("mouseover", function () {
        this.classList.add("removeBorder");
        pwdBorder.classList.add("focus");
        lock.classList.add("active");
    });
    pwdWrap.addEventListener("mouseout", function () {
        if(!passwordInput.value.trim()){
            this.classList.remove("removeBorder");
            pwdBorder.classList.remove("focus");
            lock.classList.remove("active");
        }
    });



    $(":text,:password").keyup(function (e) {
        var keyCode = e.keyCode;
        if (keyCode != 13) {
            return false;
        }
        var thisName = this.name;
        if (thisName == "username") {
            $("[name='password']").focus();
        }
        if (thisName == "password") {
            verSubmit();
        }
    });

    if (client.isNotBlank($("#errorMsg").val())) {
        alert($("#errorMsg").val());
    }

    $('#loginBtn').click(function() {
        var username = $.trim($("#username").val());
        var pwd = $.trim($("#password").val());
        if (!username || !pwd) {
            message("账号或密码不能为空");
            return false;
        } else if (pwd.length < 6) {
            message("密码不能少于6位");
            return false;
        }
        return true;
    })

});

// function verSubmit() {
//     var username = $.trim($("#username").val());
//     var pwd = $.trim($("#password").val());
//     debugger;
//     if (!username || !pwd) {
//         message("账号或密码不能为空");
//         return;
//     } else if (pwd.length < 6) {
//         message("密码不能少于6位");
//         return;
//     }
//     $('#loginForm').submit();
// }

function tologin() {
    window.location.href = "/";
}

/*//判断当前窗口是否有顶级窗口，如果有就让当前的窗口的地址栏发生变化，
function loadTopWindow(){
    if (top != window)
        top.location.href = window.location.href;
}*/


