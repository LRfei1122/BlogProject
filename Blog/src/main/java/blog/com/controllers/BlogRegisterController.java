package blog.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.models.entity.Account;
import blog.com.services.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogRegisterController {
	@Autowired
	private BlogService blogService;

	@Autowired
	private HttpSession session;

	// blog登録画面表示
	@GetMapping("/blog/register")
	public String getBlogRegister(Model model) {
		// ("account")に情報入れる
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			// もしaccount == null、loginにredirect
			return "redirect:/account/login";
		} else {
			/*
			 * そうでない、modelを使って、addAttributeで、 次の画面で、loginして人の名前を表示する
			 * accountクラスからAccountNameを取得し、"accountName"で名前する
			 */
			model.addAttribute("accountName", account.getAccountName());
			// blog-register画面表示
			return "blog-register.html";
		}
	}

	// MultipartFile fileインターフェース
	// Blog 編集処理
	@PostMapping("/account/blog/register/process")
	public String accountBlogRegisterProcess(@RequestParam String blogTitle, @RequestParam String categoryName,
			@RequestParam MultipartFile blogImage, @RequestParam String article) {
		// sessionからloginしてる人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("account");
		// もしaccount == null、loginにredirect
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// そうでない、fileの名前を取得
			/*
			 * fileの名前は重複にならないため "yyyy-MM-dd-HH-mm-ss"のような、時間で設定する。 SimpleDateFormatは時間の形
			 * getOriginalFilename()はfile元の名前 （時間＋元の名前）
			 */
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())
					+ blogImage.getOriginalFilename();

			/*
			 * Files.copy()を使う時、多様な問題が起こる可能性があるため try catchを使う。
			 */
			// blog-imgへcopyする処理
			try {
				Files.copy(blogImage.getInputStream(), Path.of("./images/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// createBlogのcreateBlogを呼び出す
			if (blogService.createBlog(blogTitle, categoryName, article, fileName, account.getAccountId())) {
				// もし、同じ物がなかったら保存
				return "redirect:/blog/list";
			} else {
				// そうでない 登録画面に止まる
				return "redirect:/blog/register";
			}

		}

	}
}
