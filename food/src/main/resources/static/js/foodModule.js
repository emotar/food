var foodModule = function() {



	// --------------------------------viewModel 宣告
	var userManagedFoodArray = ko.observableArray([]);
	var userManagedFoodArrayPagesize = ko.observable(10);
	var userManagedFoodArrayPage = ko.observable(1);
	var foodArray = ko.observableArray([]);
	var searchFoodArray = ko.observableArray([]);
	var foodOperationURL = ko.observable();
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
	var sortField = ko.observable("title");


	/**
	 * 搜尋關鍵字檢查
	 */
	var isSearchWordNotEmpty = ko.computed(function() {
        return searchWord() != undefined && $.trim(searchWord()) !== "";
    }, this);



	var setSortField1 = function(field) {

		sortField(field);
		doSort();
	}

	var doSort = function () {
		var field = sortField();
		userManagedFoodArray.sort(function(left, right) {
			return left[field] == right[field] ? 0 : left[field] < right[field] ? -1 : 1;
		});
	}

	/**
	 * 搜尋金額欄位檢查
	 */
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



	var util = utils();
	var client = null;
	var userClient = null;
	//---------------------------------------常數
	var UPDATE_FOOD_URL = "member/updateFood";
	var ADD_FOOD_URL = "member/addFood";








	//------------------------------------------Model 類別定義
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


	var Food = function(obj) {
		var self = this;
		if (!obj) {
			self.id = ko.observable();
			self.title = ko.observable();
			self.price = ko.observable();
			self.category = ko.observableArray([]);
			self.region = ko.observableArray([]);
			self.file = ko.observable();
			self.description = ko.observable();
			self.user = ko.observable();


		} else {
			self.id = ko.observable(obj.id);
			self.title = ko.observable(obj.title);
			self.price = ko.observable(obj.price);
			self.category = ko.observableArray([obj.category.id + ""]);
			self.region = ko.observableArray([obj.region.id + ""]);
			self.file = ko.observable(obj.image);
			self.description = ko.observable(obj.description);
			self.user = ko.observable(obj.user.id);



		}
	}



	//--------------------------------表單 viewModel
	var userForm = ko.observable(new User());
	var foodForm = ko.observable(new Food(null));



	/**
	 * ajax 移除美食圖片
	 */
	function removeImage(food) {
		var isChecked = $("#removeImageId").prop("checked");


		if (isChecked) {
			if (confirm('確認要刪除圖片')) {
				client.removeFoodImageById(function(data) {
					if (data.status != "ok") {
						alert("圖片刪除失敗");
					} else {
						food.file(null);
						findByUsername();
					}

				}, ko.toJS(food).id);

			} else {
				$("#removeImageId").prop("checked", false);

			}

		}

	}

	/**
	 * ajax 更新或新增美食
	 */
	function uploadFile() {
		  $.ajax({
		    url: $("#baseURL").val() + "/" + foodOperationURL(),
		    type: "POST",
		    data: new FormData($("#upload-file-form")[0]),
		    enctype: 'multipart/form-data',
		    processData: false,
		    contentType: false,
		    cache: false,
		    success: function (data) {
		    	if (data.status === "ok" && foodOperationURL() === ADD_FOOD_URL) {
		    		alert("資料新增成功");

		    	} else if (data.status === "ok" && foodOperationURL() === UPDATE_FOOD_URL) {
		    		alert("資料更新成功");
		    	}
		    	foodForm(new Food());
		    	//$("#upload-file-form")[0].reset();
		    	findByUsername();
		    	//userManagedFoodArray.push(data.data[0]);


		    },
		    error: function () {
		    	alert("資料新增失敗");
		    }
		  });

	}


	/**
	 * 帶出新增美食表單
	 */
	function toAddFood() {
		foodOperationURL(ADD_FOOD_URL);
		foodForm(new Food());
		$('#demo').collapse('toggle');

	}


	/**
	 * 帶出更新美食表單
	 */
	function toUpdateFood(food) {
		console.log(JSON.stringify(food, null, 4));
		foodOperationURL(UPDATE_FOOD_URL);
		foodForm(new Food(food));
		var eClass = $("#demo").attr("class");

		if (eClass.indexOf(" in") < 0) {
			$('#demo').collapse('show');
		}



	}


	/**
	 * ajax 刪除美食
	 */
	function removeFood(food) {
		var foodObj = ko.toJS(food);
		if (confirm("確認要刪除此筆資料")) {
			client.removeFoodById(function(data) {
				console.log(JSON.stringify(data));
				if (data.status === "ok") {
					alert("刪除資料成功");
					userManagedFoodArray.remove(food);
					foodForm(new Food());
				} else {
					alert(data.message);
				}
			}, foodObj.id);

		}

	}

	/**
	 * ajax 搜尋美食
	 *
	 */
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

	/**
	 * 根據使用者名稱搜尋美食
	 */
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


	/**
	 * 檢查使用者的存在
	 *
	 */
	function checkUsernameDuplicate(value) {

		userClient.findByUsername(function(data) {
			if (data.status !== "ok") {
				isUserExist(true);
			} else {
				isUserExist(false);
			}
		}, value);
	}


	/**
	 * 更新頁數並重新查詢
	 */
	function nextPage() {
		page(page() + 1);
		reloadByPage();
	}

	/**
	 * 更新頁數並重新查詢
	 */
	function prevPage() {
		page(page() - 1);
		reloadByPage();
	}


	/**
	 * 改變頁面顯示數量下拉式選單重新查詢
	 */
	function updatePage() {
		page(1);
		reloadByPage();
	}


	/**
	 * User 註冊驗證，並送出表單
	 */
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
		uploadFile: uploadFile,
		toAddFood: toAddFood,
		toUpdateFood: toUpdateFood,
		foodForm: foodForm,
		removeImage: removeImage,
		setSortField1: setSortField1


	}

}();