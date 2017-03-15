var foodModule = function() {


	var userManagedFoodArray = ko.observableArray([]);
	var userManagedFoodArrayPagesize = ko.observable(10);
	var userManagedFoodArrayPage = ko.observable(1);
	var foodArray = ko.observableArray([]);
	var searchFoodArray = ko.observableArray([]);
	var page = ko.observable(1);
	var size = ko.observable(3);
	var totalPage = ko.observable();
	var type = ko.observable("all");
	var categoryId = ko.observable();
	var regionId = ko.observable();
	var isUserExist = ko.observable(false);
	var pageSizeSelection = ko.observable([3, 10, 20]);
	var searchWord = ko.observable();
	var searchType = ko.observableArray(['description']);
	var searchPageSize = ko.observable(10);
	var searchPage = ko.observable(1);
	var priceStart = ko.observable(0);
	var priceEnd = ko.observable(0);



	var isSearchWordNotEmpty = ko.computed(function() {
        return searchWord() != undefined && $.trim(searchWord()) !== "";
    }, this);

	var priceNotEmpty = ko.computed(function() {
		var ps = undefined;
		var pe = undefined;

		if (priceStart() != "" && priceEnd() != "") {
			ps = parseInt(priceStart());
			pe = parseInt(priceEnd());

			if (ps > 0 && pe > 0) {
				return ps < pe;
			} else {
				return false;
			}

		} else if (priceStart() != "") {
			ps = parseInt(priceStart());
			return ps > 0;

		} else if (priceEnd() != "") {
			pe = parseInt(priceEnd());
			return pe > 0;
		}



    }, this);


	var client = null;
	var userClient = null;

	var User = function() {
		var self = this;
		self.username = ko.observable().extend({required: {params: true, message: '帳號不可為空'}});
		self.password = ko.observable().extend({minLength: {params: 8, message: '密碼長度必須大於8'}, required: {params: true, message: '密碼不可為空'}});
		self.password2 = ko.observable().extend({isfieldSame: self.password()});
		self.firstName = ko.observable();
		self.lastName = ko.observable();
		self.birthday = ko.observable();
		self.gender = ko.observable();
		self.address = ko.observable();
		self.mobile = ko.observable().extend({pattern: {params: '^09\\d{8}$', message: '手機格式不正確'}});
	}

	var userForm = ko.observable(new User());



	function uploadFile() {
		  $.ajax({
		    url: root + "/member/addFood",
		    type: "POST",
		    data: new FormData($("#upload-file-form")[0]),
		    enctype: 'multipart/form-data',
		    processData: false,
		    contentType: false,
		    cache: false,
		    success: function (data) {
		    	if (data.status === "ok") {

		    		alert("資料新增成功");
		    		$("#upload-file-form")[0].reset();

		    		userManagedFoodArray.push(data.data[0]);

		    	}
		    },
		    error: function () {
		    	alert("資料新增失敗");
		    }
		  });

	} // function uploadFile



	function removeFood(food) {
		var foodObj = ko.toJS(food);


		if (confirm("確認要刪除此筆資料")) {
			client.removeFoodById(function(data) {
				console.log(JSON.stringify(data));
				if (data.status === "ok") {
					alert("刪除資料成功");
					userManagedFoodArray.remove(food);
				} else {
					alert(data.message);
				}
			}, foodObj.id);

		}

	}
	function doSearchFood() {
		var word = $.trim(searchWord());
		var type = searchType();
		searchFoodArray([]);
		client.doSearchFood(function(data) {
			console.log(JSON.stringify(data, null, 4));

			if (data.status === "ok") {
				$.each(data.data, function(index, item) {
					searchFoodArray.push(item);

				});


				$("#searchModal").modal();

			}


		}, word, type, searchPageSize(), searchPage(), priceStart(), priceEnd());


	}

	function findByUsername() {
		userManagedFoodArray([]);
		client.findByUsernamePage(function(data){
			//console.log(JSON.stringify(data, null, 4));
			if (data.status === "ok") {
				$.each(data.data, function(index, item) {
					userManagedFoodArray.push(item);
					console.log(JSON.stringify(item, null, 4));
				});
			}


		}, userManagedFoodArrayPage(),
			userManagedFoodArrayPagesize(),
				"jerry")
	}

	function checkUsernameDuplicate(value) {

		userClient.findByUsername(function(data) {
			if (data.status !== "ok") {
				isUserExist(true);
			} else {
				isUserExist(false);
			}
		}, value);
	}


	function nextPage() {
		page(page() + 1);
		reloadByPage();
	}

	function prevPage() {
		page(page() - 1);
		reloadByPage();
	}


	function updatePage() {
		page(1);
		reloadByPage();
	}

	function addUser() {
		userForm.errors = ko.validation.group(userForm, {deep: false});

		if (userForm.errors().length > 0) {
			userForm.errors.showAllMessages();
			return false;
		} else {

			return true;
		}


	}

	/**
	 * 換頁時的頁面處理
	 *
	 */
	function reloadByPage() {
		foodArray([]);
		if (type() === "all") {
			getAllFoodByPage();

		} else if (type() === "category") {
			$("#categoryId_" + categoryId()).trigger("click");

		} else if (type() === "region") {
			getFoodByRangePage();
		}
	}


	function init() {
		var tabValue = $("#tabValue").val();


		client = foodClient($("#baseURL").val());
		userClient = UserClient($("#baseURL").val());



		if (tabValue === "food") {
			$('#tree').on("select_node.jstree", function(event, data){
				type("region");
				page(1);
				regionId(data.selected);
				foodArray([]);
				getFoodByRangePage();



			}).jstree({
				'core': {
					'data': JSON.parse($("#regionData").val()),
					'multiple': false
				}
			});



			$("[id^='categoryId_']").on("click", function(event) {
				var tmpId = $(event.target).attr("id").split("categoryId_")[1];
				if (type() !== "category") {
					page(1);
				}

				categoryId(tmpId);
				type("category");
				foodArray([]);
				client.findByCategoryPage(function(data) {
					if (data.status !== "ok") {
						alert(data.message);
					} else {
						totalPage(data.totalPages);
						page(data.currentPage);

						$.each(data.data, function(index, item) {
							foodArray.push(item);
						});
					}
				}, page(), size(), categoryId());
			})



			getAllFoodByPage();

		}



		ko.validation.init();


		//增加自行定義的 validation rule
		ko.validation.rules['isfieldSame'] = {
			    getValue: function (o) {
			        return (typeof o === 'function' ? o() : o);
			    },
			    validator: function (val, otherField) {
			    	console.log("val: " + val);
			    	console.log("otherField: " + otherField);
			        return val === this.getValue(otherField);
			    },
			    message: '確認密碼欄位不正確'
		};


		ko.applyBindings(foodModule);


		//註冊自行定義的 validation rule

		ko.validation.registerExtenders();

	};



	/**
	 * 根據頁面大小與頁面索引查詢資訊
	 * */
	function getAllFoodByPage() {
		type("all");
		client.queryAllFoodByPage(function(data) {
			console.log(JSON.stringify(data, null, 4));
			if (data.status !== "ok") {
				alert(data.message);
			} else {
				totalPage(data.totalPages);
				page(data.currentPage);
				$.each(data.data, function(index, item) {
					foodArray.push(item);
				});
			}
		}, page(), size());
	}


	function getFoodByRangePage() {
		client.findByRegionPage(function(data) {
			if (data.status !== "ok") {
				alert(data.message);
			} else {
				totalPage(data.totalPages);
				page(data.currentPage);

				$.each(data.data, function(index, item) {
					foodArray.push(item);
				});
			}
		}, page(), size(), regionId());


	}







	$(init);

	return {
		foodArray: foodArray,
		nextPage: nextPage,
		prevPage: prevPage,
		page: page,
		size: size,
		totalPage: totalPage,
		userForm: userForm,
		addUser: addUser,
		isUserExist: isUserExist,
		checkUsernameDuplicate: checkUsernameDuplicate,
		pageSizeSelection: pageSizeSelection,
		updatePage: updatePage,
		searchWord: searchWord,
		searchType: searchType,
		doSearchFood: doSearchFood,
		isSearchWordNotEmpty: isSearchWordNotEmpty,
		priceStart: priceStart,
		priceEnd: priceEnd,
		priceNotEmpty: priceNotEmpty,
		searchFoodArray: searchFoodArray,
		userManagedFoodArray: userManagedFoodArray,
		userManagedFoodArrayPagesize: userManagedFoodArrayPagesize,
		userManagedFoodArrayPage: userManagedFoodArrayPage,
		findByUsername: findByUsername,
		removeFood: removeFood,
		uploadFile: uploadFile



	}

}();