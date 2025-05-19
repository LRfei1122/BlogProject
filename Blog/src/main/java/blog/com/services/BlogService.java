package blog.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.BlogDao;
import blog.com.models.entity.Blog;

//User-Controller-"Service"-Dao-teble
//サービス層（Service）：DAO層をくみあわせて使用し、controllerへ利用可能なserviceを提供。
@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;

	// blog listのcheck
	// Blogの情報を取得する時で使う、
	public List<Blog> selectAllBlogList(Long accountId) {

		if (accountId == null) {
			return null;
		} else {
			return blogDao.findAll();
		}
	}

	// blogを作成する時の処理
	public boolean createBlog(String blogTitle, String categoryName, String article, String blogImage, Long accountId) {
		if (blogDao.findByBlogTitle(blogTitle) == null) {
			// もし、同じタイトルのblogがない DaoクラスのfindByBlogTitle
			// blogTitle==null
			blogDao.save(new Blog(blogTitle, categoryName, blogImage, article, accountId));
			// 新しblogを作る
			return true;
		} else {
			// そうでない、findByBlogTitleの情報をcontrollerに渡す
			return false;
		}
	}

	// 編集画面を表示する時のcheck
	public Blog blogEditCheck(Long blogId) {
		if (blogId == null) {
			// もしblogId == nullならば、nullを返す
			return null;
		} else {
			// そうでない
			// DaoクラスのfindByBlogIdを使って、Controllerに渡す
			return blogDao.findByBlogId(blogId);
		}
	}

	// 編集のデータ更新用
	public boolean blogUpdate(Long blogId, String blogTitle, String categoryName, String blogImage, String article,
			Long accountId) {
		if (blogId == null) {
			return false;
		} else {
			Blog blog = blogDao.findByBlogId(blogId);
			blog.setBlogTitle(blogTitle);
			blog.setCategoryName(categoryName);
			blog.setBlogImage(blogImage);
			blog.setArticle(article);
			blog.setAccountId(accountId);
			blogDao.save(blog);
			return true;
		}
	}

	// 削除処理のcheck
	// もし、controllerからもらった blogIdがあれば
	// 削除できないこと false
	// そうでない、deleteByblogIdを使用して商品の削除
	public boolean deleteByBlog(Long blogId) {
		if (blogId == null) {
			return false;
		} else {
			blogDao.deleteByBlogId(blogId);
			return true;
		}
	}

}
