var foodModule = function() {



	var foodArray = ko.observableArray([]);
	var page = ko.observable(1);
	var size = ko.observable(3);
	var totalPage = ko.observable();
	var type = ko.observable("all");
	var categoryId = ko.observable();
	var regionId = ko.observable();
	var isUserExist = ko.observable(false);
	var pageSizeSelection = ko.observable([3, 10, 20]);

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
		client = foodClient($("#baseURL").val());
		userClient = UserClient($("#baseURL").val());

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
		updatePage: updatePage
	}

}();