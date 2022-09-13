$( document ).ready(function() {
	initData();

    $("#customerForm").submit(function(event) {
		event.preventDefault();
		ajaxPost();
	});

	$("#currency1").change(function() {
		$("#result").val('');
	});

	$("#currency2").change(function() {
		$("#result").val('');
	});

    function ajaxPost(){
		getData($("#currency1").val(), $("#currency2").val(), $("#amount").val())
    }

    function getData(base, targetCurrency, amount){
		let endpoint = 'live'
		let access_key = '4xE9sxUn0lf7mXd66w3xP42gg2JctPTx';
		$.ajax({
				type : "GET",
				async: false,
				contentType : "application/json",
				url: 'https://api.apilayer.com/currency_data/' + endpoint + '?apikey=' + access_key + '&source=' + base,
				dataType: 'json',
				success: function(json) {
					sendRates(json.quotes, base, targetCurrency, amount)
				},
				error : function(e) {
					console.log('cant find data in https://api.exchangeratesapi.io/v1/', e);
					$.ajax({
						type : "GET",
						async: false,
						contentType : "application/json",
						url : window.location + "api/testData",
						dataType : 'json',
						success : function(result) {
							sendRates(result, base, targetCurrency, amount)
						},
						error : function(e) {
							alert("Error!")
							console.log("ERROR: ", e);
						}
					});
			}
		}
		)
    }

	function sendRates(ratesData, baseCurrency, targetCurrency, amount) {
		var formData = {
			ratesData : JSON.stringify(ratesData),
			baseCurrency : baseCurrency,
			targetCurrency :  targetCurrency,
			amount :  amount
		}

		$.ajax({
			type : "POST",
			async: false,
			contentType: "application/json; charset=utf-8",
			url : window.location.href + "api/convert",
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

	function initData(){
		let endpoint = 'list'
		let access_key = '4xE9sxUn0lf7mXd66w3xP42gg2JctPTx';
		$.ajax({
				type : "GET",
				contentType : "application/json",
				url: 'https://api.apilayer.com/currency_data/' + endpoint + '?apikey=' + access_key,
				dataType: 'json',
				success: function(result) {
					var ratesList = result.currencies;
					var select1 = document.getElementById("currency1");
					var select2 = document.getElementById("currency2");
					fillCurrency(select1, ratesList);
					fillCurrency(select2, ratesList);
				},
				error : function(e) {
					console.log('cant find data in https://api.apilayer.com/', e);
					alert("Can't receive currency data from server")
					$.ajax({
						type : "GET",
						async: false,
						contentType : "application/json",
						url : window.location + "api/testRates",
						dataType : 'json',
						success : function(result) {
							var select1 = document.getElementById("currency1");
							var select2 = document.getElementById("currency2");
							fillDefaultCurrency(select1, result);
							fillDefaultCurrency(select2, result);
						},
						error : function(e) {
							alert("Error!")
							console.log("ERROR: ", e);
						}
					});
				}
			}
		)
	}

	function fillCurrency(select, result) {
		for (const [key, value] of Object.entries(result)) {
			var option = document.createElement('option');
			option.value = key;
			option.text = key + ' (' + value + ')';
			select.appendChild(option);
		}
		select.removeChild(select[0]);
	}

	function fillDefaultCurrency(select, result) {
		for (var i = 0, len = result.length; i < len; ++i) {
			var rate = result[i];
			var option = document.createElement('option');
			option.value = rate;
			option.text = rate;
			select.appendChild(option);
		}
		select.removeChild(select[0]);
	}
})