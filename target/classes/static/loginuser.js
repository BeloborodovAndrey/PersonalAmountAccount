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
				type : "POST",
				async: false,
				contentType : "application/json",
				url : window.location.origin + "/signIn?login=" + login + "&password=" + password,
				dataType: 'json',
				success: function(json) {
					if(json.token != null) {
						window.location.href = window.location.origin + "/home?token=" + json.token;
					} else {
						alert('this user is not registered or has wrong password');
					}
				},
				error : function(e) {
					alert('some problems with server' + e.toString());
			}
		}
		)
    }
})