package blog.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.Blog;

//Daoはinterface作成する時はJpaRepositoryをaddする
//User-Controller-Service-"Dao"-teble
//永続層（Repository）データベースを直接操作して、永続的な操作を行う。
@Repository
public interface BlogDao extends JpaRepository<Blog, Long> {
//entityのBlogをimport、PKのLongを書く

	// 保存と更新
	// 存在する場合は保存 存在しない場合は更新
	// 新しいBlogをデータベースに生成し、最終的にテーブルに生成された情報を返す
	Blog save(Blog blog);

	// SELECT * FROM blog
	// blog listを表示させる時に使用する
	// すべてのレコードを読み
	List<Blog> findAll();

}
