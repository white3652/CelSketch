function validateForm() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPw = document.getElementById("confirmPw").value;
    let isValid = true;

    if (!validateEmail(email)) {
        document.getElementById("emailinfo").classList.remove('hide');
        isValid = false;
    } else {
        document.getElementById("emailinfo").classList.add('hide');
    }

    if (!validatePassword(password)) {
        document.getElementById("pwinfo").classList.remove('hide');
        isValid = false;
    } else {
        document.getElementById("pwinfo").classList.add('hide');
    }

    if (password !== confirmPw) {
        document.getElementById("pwinfo3").classList.remove('hide');
        isValid = false;
    } else {
        document.getElementById("pwinfo3").classList.add('hide');
    }

    return isValid;
}

function validateEmail(email) {
    const re = /^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$/;
    return re.test(email);
}

function validatePassword(password) {
    const re = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
    return re.test(password);
}

window.onload = function() {
    const elinputemail = document.querySelector("#email");
    const elinputPw = document.querySelector("#password");
    const elinputPwRe = document.querySelector("#confirmPw");

    elinputemail.onkeyup = function() {
        const emailinfo = document.getElementById("emailinfo");
        if (validateEmail(elinputemail.value)) {
            emailinfo.classList.add('hide');
        } else {
            emailinfo.classList.remove('hide');
        }
    }

    elinputPw.onkeyup = function() {
        const pwinfo = document.getElementById("pwinfo");
        const pwinfo2 = document.getElementById("pwinfo2");
        const pwinfo5 = document.getElementById("pwinfo5");

        if (elinputPw.value.length === 0) {
            pwinfo2.classList.remove('hide');
            pwinfo.classList.add('hide');
            pwinfo5.classList.add('hide');
        } else if (!validatePassword(elinputPw.value)) {
            pwinfo.classList.remove('hide');
            pwinfo2.classList.add('hide');
            pwinfo5.classList.add('hide');
        } else {
            pwinfo.classList.add('hide');
            pwinfo2.classList.add('hide');
            pwinfo5.classList.add('hide');
        }
    }

    elinputPwRe.onkeyup = function() {
        const pwinfo3 = document.getElementById("pwinfo3");

        if (elinputPwRe.value !== elinputPw.value) {
            pwinfo3.classList.remove('hide');
        } else {
            pwinfo3.classList.add('hide');
        }
    }
}