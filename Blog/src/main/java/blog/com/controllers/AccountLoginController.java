package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.models.entity.Account;
import blog.com.services.AccountService;
import jakarta.servlet.http.HttpSession;

//これがHTMLページを返すControllerであることを示すもの。
@Controller
public class AccountLoginController {
	// @Autowired AccountServiceの実装を自動的に注入する。
	@Autowired
	private AccountService accountService;

	// Sessionが使える宣言
	@Autowired
	private HttpSession session;

	// Login画面表示

	/*
	 * @GetMapping("/")は Springに、ユーザーが URL「http://localhost:8080/」にアクセスする時
	 * HTTPリクエストに応答するなら、この index() メソッドを使用するように指示します。
	 * 「"/"」の部分は、URLを指示し「localhost:8080 」以降の部分を表す
	 */
	@GetMapping("/account/login")
	public String getAccountLoginPage() {
		return "login.html";
	}

	// Login処理
	// @PostMapping 「localhost:8080/login」に送信されるPOSTリクエストを処理することを表す。
	@PostMapping("/account/login/process")
	public String accountLoginProcess(@RequestParam String email, @RequestParam String password) {
		Account account = accountService.loginCheck(email, password);
		if (account == null) {
			return "login.html";
		} else {
			session.setAttribute("account", account);
			return "redirect:/blog/list";
		}
	}
}
