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




	var doSearchFood = function(callback, searchWord,
									searchType, searchPageSize,
											searchPage, priceStart, priceEnd) {



		util.ajaxCall(baseUrl + "/queryFoodBySearchType",
							"searchWord=" + searchWord + "&searchType=" +
								searchType + "&searchPageSize=" + searchPageSize + "&searchPage=" + searchPage +
									"&priceStart=" + priceStart + "&priceEnd=" + priceEnd,
					callback);
	}

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


	var findByUsernamePage = function(callback, page, size, username) {
		util.ajaxCall(baseUrl + "/findByUsernamePage",
				"page=" + page + "&size=" + size + "&username=" + username,
					callback);
	}


	var removeFoodById = function(callback, foodId) {
		util.ajaxCall(baseUrl + "/removeFoodById",
							"foodId=" + foodId,
								callback);
	}


	var removeFoodImageById = function(callback, foodId) {
		util.ajaxCall(baseUrl + "/member/removeFoodImageById",
				"foodId=" + foodId,
					callback);

	}


	return {
		queryAllFood: queryAllFood,
		queryAllFoodByPage: queryAllFoodByPage,
		findByCategoryPage: findByCategoryPage,
		findByRegionPage: findByRegionPage,
		doSearchFood: doSearchFood,
		findByUsernamePage: findByUsernamePage,
		removeFoodById: removeFoodById,
		removeFoodImageById: removeFoodImageById

	}

}