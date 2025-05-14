package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.services.AccountService;

//これがHTMLページを返すControllerであることを示すもの。
@Controller
public class AccountRegisterController {
	// @Autowired AccountServiceの実装を自動的に注入する。
	@Autowired
	private AccountService accountService;

	// 登録画面表示
	/*
	 * 「Get」は、GET メソッドによるリクエストに応答すること
	 * 
	 * @GetMapping("/")は Springに、ユーザーが URL「http://localhost:8080/」にアクセスする時
	 * HTTPリクエストに応答するなら、この index() メソッドを使用するように指示します。
	 * 「"/"」の部分は、URLを指示し「localhost:8080 」以降の部分を表す
	 */
	@GetMapping("/account/register")
	public String getAccountRegister() {
		return "register.html";
	}

	// 登録処理
	@PostMapping("/account/register/process")

	/*
	 * @PostMapping 「localhost:8080/login」に送信されるPOSTリクエストを処理することを表す。
	 * POSTメソッドは、個人情報や大量のデータをサーバに送信するために使用する。
	 * 
	 * 入力したデータをサーバに送信するために、<input> の name 属性でパラメータ名を設定する。 Ex:<form>の<input name
	 * ="email">と完全一致させる。
	 * 
	 * @RequestParam このようなパラメータを持つことを示し。
	 * 
	 */
	public String accountRegisterProcess(@RequestParam String userName, @RequestParam String email,
			@RequestParam String password) {
		// accountを作成したら、ログイン画面へ移動
		if (accountService.createAccount(userName, email, password)) {
			return "login.html";
		} else {
			// そうでない、登録画面に戻る
			return "register.html";
		}
	}
}
