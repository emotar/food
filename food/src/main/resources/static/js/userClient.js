var UserClient = function(url) {
	var baseUrl = url;
	var util = utils();



	var findByUsername = function(callback, username) {
		util.ajaxCall(baseUrl + "/isUserExist",
						"username=" + username,
							callback);
	}





	return {
		findByUsername: findByUsername
	}

}