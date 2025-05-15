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

}
