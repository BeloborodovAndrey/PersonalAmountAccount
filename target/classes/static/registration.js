$(document).ready(function() {
    $("#registerForm").submit(function(event) {
		event.preventDefault();
		ajaxPost();
	});

    function ajaxPost(){
		putData($("#login").val(), $("#password").val())
    }

    function putData(login, password){
		$.ajax({
				type : "PUT",
				async: false,
				contentType : "application/json",
				url : window.location.href + "?login=" + login + "&password=" + password,
				dataType: 'json',
				success: function(json) {
					if(json === true) {
						alert('user has been registered')
						window.location.href = window.location.origin + "/login";
					} else {
						alert('this user already registered');
						window.location.href = window.location.origin + "/login";
					}
				},
				error : function(e) {
					alert('some problems with server' + e.toString());
			}
		}
		)
    }

})