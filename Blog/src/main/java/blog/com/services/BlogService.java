package blog.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.BlogDao;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;
}
