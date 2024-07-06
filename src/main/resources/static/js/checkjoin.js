let emailvalid = false;
let idvalid = false;
let pwvalid = false;
let pwvalidre = false;
let nicknamevalid = false;
let hpvalid = false;

function checkemailva(str) {
    return /^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$/.test(str);
}

function idLength(value) {
    return value.length >= 2 && value.length <= 20;
}

function onlyNumberAndEnglish(str) {
    return /^(?=.*[0-9])[a-zA-Z][a-zA-Z0-9]*$/.test(str);
}

function pwLength(value) {
    return value.length >= 8 && value.length <= 16;
}

function checkpwva(str) {
    return /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test(str);
}

function checknicknameva(str) {
    return /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$/.test(str);
}

function disable() {
    if (idvalid && emailvalid && pwvalid && pwvalidre) {
        document.getElementById("goToPage2").disabled = false;
    } else {
        document.getElementById("goToPage2").disabled = true;
    }
}

function main1() {
    if (nicknamevalid && hpvalid) {
        document.getElementById("submit").disabled = false;
    } else {
        document.getElementById("submit").disabled = true;
    }
}

const autoHyphen = (target) => {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

async function checkUserId() {
    const userId = document.querySelector("#userId").value;

    // 초기 모든 메시지를 숨김
    document.querySelector('#idinfo1').classList.add('hide');
    document.querySelector('#idinfo4').classList.add('hide');
    document.querySelector('#idinfo').classList.add('hide');
    document.querySelector('#idinfo2').classList.add('hide');
    document.querySelector('#idinfo3').classList.add('hide');

    if (!idLength(userId) || !onlyNumberAndEnglish(userId)) {
        document.querySelector('#idinfo').classList.remove('hide');
        idvalid = false;
        disable();
        return;
    }

    try {
        const response = await fetch(`/user/check-id?userId=${userId}`);
        const data = await response.json();

        if (!data) {
            document.querySelector('#idinfo4').classList.remove('hide');
            idvalid = false;
        } else {
            document.querySelector('#idinfo1').classList.remove('hide');
            idvalid = true;
        }
    } catch (error) {
        console.error('Error checking user ID:', error);
        idvalid = false;
    }

    disable();
}

window.onload = function () {
    const elinputemail = document.querySelector("#email");
    const elSuccessEmailMessage = document.querySelector('#emailinfo1');
    const elFailureEmailMessage = document.querySelector('#emailinfo');
    const elFailureEmailMessageTwo = document.querySelector('#emailinfo2');

    const elinputid = document.querySelector("#userId");
    const elSuccessIdMessage = document.querySelector('#idinfo1');
    const elFailureIdMessage = document.querySelector('#idinfo');
    const elFailureIdMessageTwo = document.querySelector('#idinfo2');
    const elFailureIdMessageThr = document.querySelector('#idinfo3');
    const elFailureIdMessageFour = document.querySelector('#idinfo4');

    const elinputPw = document.querySelector("#password");
    const elSuccessPwMessage = document.querySelector('#pwinfo1');
    const elFailurePwMessage = document.querySelector('#pwinfo');
    const elFailurePwMessageTwo = document.querySelector('#pwinfo2');
    const elFailurePwMessageThr = document.querySelector('#pwinfo3');
    const elFailurePwMessageFor = document.querySelector('#pwinfo4');
    const elFailurePwMessageFar = document.querySelector('#pwinfo5');
    const elinputPwRe = document.querySelector("#confirmPw");

    const elinputnickmane = document.querySelector("#userName");
    const elSuccessNnMessage = document.querySelector('#nninfo1');
    const elFailureNnMessage = document.querySelector('#nninfo');
    const elFailureNnMessageTwo = document.querySelector('#nninfo2');

    const elinputHp = document.querySelector("#phoneNumber");

    elinputemail.onkeyup = function () {
        if (elinputemail.value.length !== 0) {
            if (checkemailva(elinputemail.value) === false) {
                elSuccessEmailMessage.classList.add('hide');
                elFailureEmailMessage.classList.remove('hide');
                elFailureEmailMessageTwo.classList.add('hide');
                emailvalid = false;
            } else {
                elSuccessEmailMessage.classList.remove('hide');
                elFailureEmailMessage.classList.add('hide');
                elFailureEmailMessageTwo.classList.add('hide');
                emailvalid = true;
            }
        } else {
            elSuccessEmailMessage.classList.add('hide');
            elFailureEmailMessage.classList.add('hide');
            elFailureEmailMessageTwo.classList.remove('hide');
            emailvalid = false;
        }

        disable();
    }

    elinputid.onkeyup = function () {
        // 초기 모든 메시지를 숨김
        elSuccessIdMessage.classList.add('hide');
        elFailureIdMessage.classList.add('hide');
        elFailureIdMessageTwo.classList.add('hide');
        elFailureIdMessageThr.classList.add('hide');
        elFailureIdMessageFour.classList.add('hide');

        if (elinputid.value.length !== 0) {
            if (onlyNumberAndEnglish(elinputid.value) === false) {
                elFailureIdMessageTwo.classList.remove('hide');
                idvalid = false;
            } else if (idLength(elinputid.value) === false) {
                elFailureIdMessage.classList.remove('hide');
                idvalid = false;
            } else if (idLength(elinputid.value) && onlyNumberAndEnglish(elinputid.value)) {
                checkUserId();
            }
        } else {
            elFailureIdMessageThr.classList.remove('hide');
            idvalid = false;
        }

        disable();
    };

    elinputPw.onkeyup = function () {
        if (elinputPw.value.length !== 0) {
            if (pwLength(elinputPw.value) === false) {
                elFailurePwMessageFar.classList.remove('hide');
                elFailurePwMessage.classList.add('hide');
                elFailurePwMessageTwo.classList.add('hide');
                pwvalid = false;
            } else if (checkpwva(elinputPw.value) === false) {
                elFailurePwMessage.classList.remove('hide');
                elFailurePwMessageTwo.classList.add('hide');
                elFailurePwMessageFar.classList.add('hide');
                pwvalid = false;
            } else if ((pwLength(elinputPw.value) && checkpwva(elinputPw.value))) {
                elFailurePwMessageFar.classList.add('hide');
                elFailurePwMessage.classList.add('hide');
                elFailurePwMessageTwo.classList.add('hide');
                pwvalid = true;
            }
        } else {
            elFailurePwMessageTwo.classList.remove('hide');
            elFailurePwMessage.classList.add('hide');
            elFailurePwMessageFar.classList.add('hide');
            pwvalid = false;
        }
        disable();
    }

    elinputPwRe.onkeyup = function () {
        if (elinputPwRe.value.length !== 0) {
            if (elinputPw.value !== elinputPwRe.value) {
                elSuccessPwMessage.classList.add('hide');
                elFailurePwMessageThr.classList.remove('hide');
                elFailurePwMessageFor.classList.add('hide');
                pwvalidre = false;
            } else {
                elSuccessPwMessage.classList.remove('hide');
                elFailurePwMessageThr.classList.add('hide');
                elFailurePwMessageFor.classList.add('hide');
                pwvalidre = true;
            }
        } else {
            elSuccessPwMessage.classList.add('hide');
            elFailurePwMessageThr.classList.add('hide');
            elFailurePwMessageFor.classList.remove('hide');
            pwvalidre = false;
        }
        disable();
    }

    elinputnickmane.onkeyup = function () {
        if (elinputnickmane.value.length !== 0) {
            if (checknicknameva(elinputnickmane.value) === false) {
                elSuccessNnMessage.classList.add('hide');
                elFailureNnMessage.classList.remove('hide');
                elFailureNnMessageTwo.classList.add('hide');
                nicknamevalid = false;
            } else {
                elSuccessNnMessage.classList.remove('hide');
                elFailureNnMessage.classList.add('hide');
                elFailureNnMessageTwo.classList.add('hide');
                nicknamevalid = true;
            }
        } else {
            elSuccessNnMessage.classList.add('hide');
            elFailureNnMessage.classList.add('hide');
            elFailureNnMessageTwo.classList.remove('hide');
            nicknamevalid = false;
        }
        main1();
    }

    elinputHp.onkeyup = function () {
        if (elinputHp.value.length !== 0) {
            hpvalid = true;
        } else {
            hpvalid = false;
        }
        main1();
    }
}

function submitForm() {
    const user = {
        email: document.querySelector("#email").value,
        userId: document.querySelector("#userId").value,
        password: document.querySelector("#password").value,
        confirmPw: document.querySelector("#confirmPw").value,
        userName: document.querySelector("#userName").value,
        gender: document.querySelector('input[name="gender"]:checked').value,
        year: document.querySelector('select[name="year"]').value,
        month: document.querySelector('select[name="month"]').value,
        day: document.querySelector('select[name="day"]').value,
        phoneNumber: document.querySelector("#phoneNumber").value,
    };

    const xmlDoc = document.implementation.createDocument("", "", null);
    const userElement = xmlDoc.createElement("user");

    for (const key in user) {
        const element = xmlDoc.createElement(key);
        element.textContent = user[key];
        userElement.appendChild(element);
    }

    xmlDoc.appendChild(userElement);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/user/join", true);
    xhr.setRequestHeader("Content-Type", "application/xml");

    xhr.onload = function () {
        if (xhr.status === 200) {
            alert("가입 성공!");
            window.location.href = "/login";
        } else {
            alert("가입 실패!");
        }
    };

    xhr.onerror = function () {
        alert("요청 실패!");
    };

    xhr.send(new XMLSerializer().serializeToString(xmlDoc));
}