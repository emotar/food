package ga.javatw.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {


	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@RequestMapping("/index")
	public String index(@RequestParam(value = "tab", required = false) String tab) {
		if (null == tab) {
			return "redirect:/member/index?tab=member";
		}
		return "user/member";
	}






}
