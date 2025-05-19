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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.models.entity.Account;
import blog.com.models.entity.Blog;
import blog.com.services.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogEditController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private HttpSession session;

	@GetMapping("/account/blog/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		// sessionからloginしてる人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			// もしaccount == null、loginにredirect
			return "redirect://account/login";
		} else {
			// 編集画面に表示される情報を変数blogに格納
			Blog blog = blogService.blogEditCheck(blogId);
			if (blog == null) {
				// blog == nullならば、blog listに戻す
				return "redirect:/blog/list";
			} else {
				// そうでない場合、編集画面に編集する内容を渡す
				model.addAttribute("accountName", account.getAccountName());
				model.addAttribute("blog", blog);
				return "blog-edit.html";
			}

		}
	}

	// Blog 編集処理
	@PostMapping("/blog/edit/process")
	public String blogUpdate(@RequestParam String blogTitle, @RequestParam String categoryName,
			@RequestParam MultipartFile blogImage, @RequestParam String article, @RequestParam Long blogId) {
		// sessionからloginしてる人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			// もしaccount == null、loginにredirect
			return "redirect:/account/login";
		} else {
			// そうでない、fileの名前を取得
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())
					+ blogImage.getOriginalFilename();

			// blog-imgへcopyする処理
			// try catch
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (blogService.blogUpdate(blogId, blogTitle, categoryName, fileName, article, account.getAccountId())) {
				return "redirect:/blog/list";
			} else {
				return "redirect:/blog/edit";
			}
		}
	}

}
