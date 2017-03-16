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


	var searchObjectIndexInArray = function(sourceArray, targetValue, sourceProperty) {
		for(i = 0; i < sourceArray.length; i++) {
			if (sourceArray[i][sourceProperty] === targetValue) {
				return i;
			}
		}
		return -1;
	}

	return {
		ajaxCall: ajaxCall,
		searchObjectIndexInArray: searchObjectIndexInArray
	}
}