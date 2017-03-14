var utils = function() {

	var ajaxCall = 	function (url, data, callback) {
		$.ajax({
		    url: url,
		    type: "POST",
		    contentType: "application/x-www-form-urlencoded",
		    async: true,
		    dataType: "json",
		    data : data,
		    success: function(result) {
				callback(result);
		    }
		});

	};


	return {
		ajaxCall: ajaxCall
	}
}