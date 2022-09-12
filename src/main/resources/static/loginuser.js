$(document).ready(function() {
    $("#loginForm").submit(function(event) {
		event.preventDefault();
		ajaxPost();
	});

    function ajaxPost(){
		getData($("#login").val(), $("#password").val())
    }

    function getData(login, password){
		$.ajax({
				type : "post",
				async: false,
				contentType : "application/json",
				url : window.location.href + "/signIn?login=" + login + "?password=" + password,
				dataType: 'json',
				success: function(json) {
					if(json === true) {
						window.location.href = window.location.href + "/home";
					} else {
						alert('this user is not registered');
					}
				},
				error : function(e) {
					alert('some problems with server' + e.toString());
			}
		}
		)
    }
})