package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import blog.com.models.entity.Account;
import blog.com.services.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogDeleteController {
	@Autowired
	private BlogService blogservice;
	@Autowired
	private HttpSession session;

	@PostMapping("/blog/delete")
	public String blogdelete(Long blogId) {
		// ("account")に情報入れる
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			// もしaccount == null、loginにredirect
			return "redirect:/account/login";
		} else {
			if (blogservice.deleteByBlog(blogId)) {
				return "redirect:/blog/list";
			} else {
				return "redirect:/blog/edit" + blogId;
			}
		}
	}
}
