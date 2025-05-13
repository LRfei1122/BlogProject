package blog.com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountRegisterController {

	// 登録画面表示
	@GetMapping("/account/register")
	public String getAccountRegister() {
		return "register.html";
	}
}
