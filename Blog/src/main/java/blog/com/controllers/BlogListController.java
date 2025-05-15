package blog.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import blog.com.models.entity.Account;
import blog.com.models.entity.Blog;
import blog.com.services.BlogService;
import jakarta.servlet.http.HttpSession;

//User-"Controller"-Service-Dao-teble
//Controller層　サービス層を使用して、実際のcontrollerメソッドを実装。
//userは直接コントロールする
@Controller
public class BlogListController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private HttpSession session;

	@GetMapping("/blog/list")
	public String getBlogList(Model model) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// Blogの情報を取得
			// Listを表示、loginしてる人の情報を画面に渡して、Blog ListのHTMLを表示する
			// BlogServiceのselectAllBlogListを使う
			List<Blog> blogList = blogService.selectAllBlogList(account.getAccountId());
			// model これを押して、blogListに飛ぶ
			model.addAttribute("accountName", account.getAccountName());
			model.addAttribute("blogList", blogList);
			return "blog-list.html";
		}
	}
}
