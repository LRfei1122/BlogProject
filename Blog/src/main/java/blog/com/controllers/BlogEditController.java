package blog.com.controllers;

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

	@GetMapping("/blog/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		// sessionからloginしてる人の情報をadminという変数に格納
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
				return "blog-edit.html";
			}

		}
	}

	@PostMapping("/account/blog/update")
	public String blogUpdate(@RequestParam String blogTitle, @RequestParam String categoryName,
			@RequestParam MultipartFile blogImage, @RequestParam String blogDetail, @RequestParam Long blogId) {
		// sessionからloginしてる人の情報をadminという変数に格納
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			// もしaccount == null、loginにredirect
			return "redirect://account/login";
		} else {

		}
	}

}
