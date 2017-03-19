package ga.javatw.user.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ga.javatw.common.AjaxResult;
import ga.javatw.common.ApplicationConstant;
import ga.javatw.common.utils.FileUtil;
import ga.javatw.food.model.Food;
import ga.javatw.food.service.FoodService;
import ga.javatw.user.model.User;
import ga.javatw.user.service.UserService;

@RestController
@RequestMapping("/ajax/member")
public class MemberAjaxController {

	private static final Logger logger = LoggerFactory.getLogger(MemberAjaxController.class);

	@Autowired
	private FoodService foodService;

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationConstant env;

	@RequestMapping("/addFood")
	public @ResponseBody AjaxResult<Food> addFood(Food newFood,
														@RequestParam("file") MultipartFile file,
															HttpServletRequest request) throws IOException {

		String fileSavePath = env.getFilesavepath();


		if (file.getSize() > 0) {
			String newFileName = FileUtil.saveFile(file, fileSavePath, file.getInputStream());
			newFood.setImage(newFileName);
		}



		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		//這裡預計使用  Spring Security 修改
		User user = userService.findByUsername(username);
		newFood.setUser(user);

		Food savedFood = foodService.saveFood(newFood);


		return new AjaxResult<Food>(savedFood);
	}


	@RequestMapping("/updateFood")
	public @ResponseBody AjaxResult<Food> updateFood(Food oldFood,
														@RequestParam("file") MultipartFile file,
															HttpServletRequest request) throws IOException {

		AjaxResult<Food> ajaxResult = null;

		String fileSavePath = env.getFilesavepath();

		if (file.getSize() > 0) {
			String directory = request.getServletContext().getRealPath("/images/foodImages");
			String newFileName = FileUtil.saveFile(file, fileSavePath, file.getInputStream());
			oldFood.setImage(newFileName);
		}


		int updateResult = foodService.updateFood(oldFood);

		if (updateResult == 1) {
			ajaxResult = new AjaxResult<Food>("ok", "");
		} else {
			ajaxResult = new AjaxResult<Food>("error", "");
		}

		return ajaxResult;

	}


	@RequestMapping("/removeFoodImageById")
	public @ResponseBody AjaxResult<Food> removeFoodImageById(@RequestParam("foodId") Long foodId) {

		foodService.removeImageById(foodId);


		return new AjaxResult<Food>("ok", "");

	}
}
