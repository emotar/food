package ga.javatw.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ga.javatw.common.AjaxResult;
import ga.javatw.user.model.User;
import ga.javatw.user.service.UserService;

@RestController
@RequestMapping("/ajax")
public class UserAjaxController {

	@Autowired
	private UserService userService;

	@RequestMapping("/isUserExist")
	public AjaxResult<String> isUserExist(@RequestParam("username") String username) {
		User user = userService.findByUsername(username);
		AjaxResult<String> ajaxResult = null;
		if (null == user) {
			ajaxResult = new AjaxResult<String>("ok", "");
		} else {
			ajaxResult = new AjaxResult<String>("error", "");
		}

		return ajaxResult;
	}
}
