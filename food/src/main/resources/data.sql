-------------------------食物種類
INSERT INTO Food_Category (title, description, cdate) VALUES ('燒烤類', null, '2017-03-14')
INSERT INTO Food_Category (title, description, cdate) VALUES ('鍋類', null, '2017-03-14')
INSERT INTO Food_Category (title, description, cdate) VALUES ('小吃', null, '2017-03-14')
INSERT INTO Food_Category (title, description, cdate) VALUES ('冰品、飲料、甜湯', null, '2017-03-14')
INSERT INTO Food_Category (title, description, cdate) VALUES ('咖啡、簡餐、茶', null, '2017-03-14')



-------------------------地區
INSERT INTO Region (id, parent, text) VALUES ('1', '#', '大臺北')
INSERT INTO Region (id, parent, text) VALUES ('2', '#', '桃竹苗')
INSERT INTO Region (id, parent, text) VALUES ('3', '#', '中彰投')
INSERT INTO Region (id, parent, text) VALUES ('4', '#', '雲嘉嘉')
INSERT INTO Region (id, parent, text) VALUES ('5', '#', '南高屏澎')
INSERT INTO Region (id, parent, text) VALUES ('6', '#', '東部')
INSERT INTO Region (id, parent, text) VALUES ('7', '1', '臺北市')
INSERT INTO Region (id, parent, text) VALUES ('8', '1', '新北市')
INSERT INTO Region (id, parent, text) VALUES ('9', '1', '基隆市')
INSERT INTO Region (id, parent, text) VALUES ('10', '2', '桃園市')
INSERT INTO Region (id, parent, text) VALUES ('11', '2', '新竹縣')
INSERT INTO Region (id, parent, text) VALUES ('12', '2', '新竹市')
INSERT INTO Region (id, parent, text) VALUES ('13', '2', '苗栗縣')
INSERT INTO Region (id, parent, text) VALUES ('14', '3', '臺中市')
INSERT INTO Region (id, parent, text) VALUES ('15', '3', '彰化縣')
INSERT INTO Region (id, parent, text) VALUES ('16', '3', '南投縣')
INSERT INTO Region (id, parent, text) VALUES ('17', '4', '雲林縣')
INSERT INTO Region (id, parent, text) VALUES ('18', '4', '嘉義縣')
INSERT INTO Region (id, parent, text) VALUES ('19', '4', '嘉義市')
INSERT INTO Region (id, parent, text) VALUES ('20', '5', '臺南市')
INSERT INTO Region (id, parent, text) VALUES ('21', '5', '高雄市')
INSERT INTO Region (id, parent, text) VALUES ('22', '5', '屏東縣')
INSERT INTO Region (id, parent, text) VALUES ('23', '5', '澎湖縣')
INSERT INTO Region (id, parent, text) VALUES ('24', '6', '宜蘭縣')
INSERT INTO Region (id, parent, text) VALUES ('25', '6', '花蓮縣')
INSERT INTO Region (id, parent, text) VALUES ('26', '6', '臺東縣')


------------------------會員群組
INSERT INTO User_Group (name, description, cdate) VALUES ('admin', '', '2017-03-14')
INSERT INTO User_Group (name, description, cdate) VALUES ('member', '', '2017-03-14')


-------------------------會員

INSERT INTO User (username, password, mobile, first_Name, last_Name, birthday, address, cdate) VALUES ('jerry', '12345', '0911111111', '王', '小明', '1981-03-14', '桃園市中壢區中正路11號', '2017-03-14')



-------------------------指派會員與群組
INSERT INTO user_group_user  (user_group_id, user_id) VALUES (SELECT id FROM User_Group WHERE name = 'admin', SELECT id FROM User WHERE username = 'jerry')
INSERT INTO user_group_user  (user_group_id, user_id) VALUES (SELECT id FROM User_Group WHERE name = 'member', SELECT id FROM User WHERE username = 'jerry')






------------------------美食
INSERT INTO Food (title, description, price, image, cdate, category_id, region_id, user_id) VALUES ('八色烤肉', '還不錯吃...', 999, '61c2bb79be7ad617615a3c73cf4475cd.jpg', '2017-03-14', SELECT id FROM Food_Category WHERE title = '燒烤類', '7', SELECT id FROM User WHERE username = 'jerry')
INSERT INTO Food (title, description, price, image, cdate, category_id, region_id, user_id) VALUES ('壽亭燒肉鍋物', '還不錯吃...', 323, 'cgma6a61c79dc45ad882f21717ba6267e1f761.jpg', '2017-03-14', SELECT id FROM Food_Category WHERE title = '燒烤類', '7', SELECT id FROM User WHERE username = 'jerry')
INSERT INTO Food (title, description, price, image, cdate, category_id, region_id, user_id) VALUES ('火之舞蓁品燒(忠孝店)', '還不錯吃...', 749, 'cgmbf3f272321fc125aa5773da59310cb4d198.jpg', '2017-03-14', SELECT id FROM Food_Category WHERE title = '燒烤類', '7', SELECT id FROM User WHERE username = 'jerry')
INSERT INTO Food (title, description, price, image, cdate, category_id, region_id, user_id) VALUES ('月島文字燒(台北忠孝SOGO店) ', '還不錯吃...', 282, 'cm20100802f8cf080fabde27201ef8fcf71df5b4df160.jpg', '2017-03-14', SELECT id FROM Food_Category WHERE title = '燒烤類', '7', SELECT id FROM User WHERE username = 'jerry')
INSERT INTO Food (title, description, price, image, cdate, category_id, region_id, user_id) VALUES ('潮肉壽喜燒', '還不錯吃...', 434, 'cgm9b4bc5663a86540854a06220cdc61514134.jpg', '2017-03-14', SELECT id FROM Food_Category WHERE title = '燒烤類', '7', SELECT id FROM User WHERE username = 'jerry')

