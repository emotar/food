var foodClient = function(url) {
	var baseUrl = url;
	var util = utils();

	var queryAllFood = function(callback) {
		$.ajax({
		    url: baseUrl + "/queryAllFood",
		    type: "GET",
		    contentType: "application/x-www-form-urlencoded",
		    async: true,
		    dataType: "json",
		    success: function(result) {
				callback(result);
		    }
		});
	}



	/*
	var queryAllFoodByPage = function(callback, page, size) {
		$.ajax({
		    url: baseUrl + "/queryAllFoodByPage",
		    type: "POST",
		    contentType: "application/x-www-form-urlencoded",
		    async: true,
		    dataType: "json",
		    data : "page=" + page + "&size=" + size,
		    success: function(result) {
				callback(result);
		    }
		});
	}
	*/

	var queryAllFoodByPage = function(callback, page, size) {
		util.ajaxCall(baseUrl + "/queryAllFoodByPage",
						"page=" + page + "&size=" + size,
							callback);
	}


	var findByCategoryPage = function(callback, page, size, categoryId) {
		util.ajaxCall(baseUrl + "/findByCategoryPage",
				"page=" + page + "&size=" + size + "&categoryId=" + categoryId,
					callback);

	}

	var findByRegionPage = function(callback, page, size, regionId) {
		util.ajaxCall(baseUrl + "/findByRegionPage",
				"page=" + page + "&size=" + size + "&regionId=" + regionId,
					callback);
	}



	return {
		queryAllFood: queryAllFood,
		queryAllFoodByPage: queryAllFoodByPage,
		findByCategoryPage: findByCategoryPage,
		findByRegionPage: findByRegionPage
	}

}