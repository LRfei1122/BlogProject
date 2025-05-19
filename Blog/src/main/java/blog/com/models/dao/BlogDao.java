package blog.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.Blog;
import jakarta.transaction.Transactional;

//Daoはinterface作成する時はJpaRepositoryをaddする
//User-Controller-Service-"Dao"-teble
//永続層（Repository）データベースを直接操作して、永続的な操作を行う。
@Repository
@Transactional
/*
 * 大事な物、データを丸ごと処理する
 * saveやdeleteなどことに対して重要
 * 全て消す、もしできないなら、そのまま置いて
 */
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

	// SELECT * FROM blog WHERE blog_title =?
	// blogの登録check 同じタイトルが登録しないようにする
	Blog findByBlogTitle(String logTitle);

	// SELECT * FROM products WHERE blog_id =?
	// 編集画面を表示際に使用
	Blog findByBlogId(Long blogId);

	// DELETE FROM blog WHERE blog_id =?
	// 削除使用
	void deleteByBlogId(Long blogId);
}
