$(document).ready(function () {

    // Variables
    var baseUrl = 'http://localhost:9000'



    // Functions
    







    // Event handlers

    $('.auth-toggle').on('click', function () {
        $('.register').toggleClass('hide-auth');
        $('.login').toggleClass('hide-auth');
    });

    $('#login-submit').on('click', function (e) {
        e.preventDefault();
        console.log('login-submit');
        $("#login-submit").prop("disabled", true);
        var req = {
            'userName': $('#lusername').val(),
            'password': $('#lpassword').val()
        }
        $.ajax({
           type: "POST",
           contentType: "application/json",
           url: baseUrl+"/api/auth/login",
           data: JSON.stringify(req),
           dataType: 'json',
           cache: false,
           timeout: 600000,
           success: function (data) {
               console.log("SUCCESS : ", data);
               $("#login-submit").prop("disabled", false);
           },
           error: function (e) {
               console.log("ERROR : ", e);
               $("#login-submit").prop("disabled", false);
           }
        });

    });

    $('#signup-submit').on('click', function (e) {
        e.preventDefault();
        console.log('signup-submit');
        $("#login-submit").prop("disabled", true);
        var req = {
            'userName': $('#rusername').val(),
            'password': $('#rpassword').val(),
            'name': $('#rname').val(),
            'email': $('#remailAddress').val(),
        }
        $.ajax({
           type: "POST",
           contentType: "application/json",
           url: baseUrl+"/api/auth/register",
           data: JSON.stringify(req),
           dataType: 'json',
           cache: false,
           timeout: 600000,
           success: function (data) {
               console.log("SUCCESS : ", data);
               $("#login-submit").prop("disabled", false);
           },
           error: function (e) {
               console.log("ERROR : ", e);
               $("#login-submit").prop("disabled", false);
           }
        });
        alert('We have send you a mail, please follow the steps in the mail to activate your account.')
    });

});