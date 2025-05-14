package blog.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.com.models.entity.Blog;

//Daoはinterface作成する時はJpaRepositoryをaddする
public interface BlogDao extends JpaRepository<Blog, Long> {
//entityのBlogをimport、PKのLongを書く

}
