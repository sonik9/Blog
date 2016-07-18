/**
 * Created by Vitalii on 7/18/2016.
 */
$(document).ready(function () {
    $.validattion();
    $('#signup').on('click', function () {
        $('#signinbox, #accountSignInText').hide();
        $('#signupbox, #accountSignUpText').show()
        $("#signup").toggleClass("active");
        $("#signin").toggleClass("active");
    });
    $('#signin').on('click', function () {
        $('#signinbox, #accountSignInText').show();
        $('#signupbox, #accountSignUpText').hide();
        $("#signup").toggleClass("active");
        $("#signin").toggleClass("active");
    });


});