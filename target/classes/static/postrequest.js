$(document).ready(function() {
	fillCurrentUserData();
	initData();
	setInterval(initData, 15000);

    $("#customerForm").submit(function(event) {
		event.preventDefault();
		ajaxPost();
	});

	$("#currency1").change(function() {
		$("#result").val('');
	});

	$("#amount").change(function() {
		const max = parseFloat($("#currentAmount").val());
		const min = 0;
		if (parseFloat($("#amount").val()) > max)
		{
			$("#amount").val(max);
		}
		else if (parseFloat($("#amount").val()) < min)
		{
			$("#amount").val(min);
		}
	});

    function ajaxPost(){
		if (parseFloat($("#currentAmount").val()) < parseFloat($("#amount").val())) {
			alert('value of amount more than current amount');
		}
		if (0 > parseFloat($("#amount").val())) {
			alert('value of amount less than zero');
		}
		sendRates($("#currency1").val(), $("#amount").val())
    }

	function sendRates(currency, amount) {
		var formData = {
			currency :  currency,
			amount :  amount
		}

		$.ajax({
			type : "POST",
			async: false,
			contentType: "application/json; charset=utf-8",
			url : window.location.origin + "/convert",
			data : JSON.stringify(formData),
			success : function(result) {
				if (result === 'parsing error') {
					alert('wrong amount value')
				}
				console.log(result);
				$("#result").val(result);
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
	}

	function initData() {
		$.ajax({
			type: "GET",
			async: false,
			contentType: "application/json",
			url: window.location.origin + "/testRates",
			dataType: 'json',
			success: function (result) {
				var select1 = document.getElementById("currency1");
				select1.options.length = 0;
				fillDefaultCurrency(select1, result);
			},
			error: function (e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
	}


	function fillCurrentUserData(){
		$.ajax({
				type : "POST",
				contentType : "application/json",
				url: window.location.origin + '/currentUser',
				dataType: 'json',
				success: function(result) {
						$("#user").val(result.username);
						$("#currentAmount").val(result.amount);
				},
				error : function(e) {
					console.log('cant find data', e);
					alert("Can't receive amount data from server")
				}
			}
		)
	}

	function fillDefaultCurrency(select, result) {
		for (const [key, value] of Object.entries(result)) {
			var option = document.createElement('option');
			option.value = key;
			option.text = key + ' (' + value + ')';
			select.appendChild(option);
		}
	}
})