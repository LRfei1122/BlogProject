package blog.com.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//@Entity このクラスがデータベースのテーブルと対応することを宣言します。
//クラス名（Blog）= テーブル名（blog）
@Entity
public class Blog {
	// tableのPRIMARY KEY
	@Id
	// データベースによって、IDが自動増加されるように設定
	@GeneratedValue(strategy = GenerationType.AUTO)

	// pgAdminのblogテーブルの設定
	private Long blogId;

	private String blogTitle;

	private String categoryName;

	private String blogImage;

	private String article;

	private Long accountId;

	// springが必要になる、空のConstuctor
	public Blog() {
	}

	// constructor
	// blogIdが自動生成のため、定義しない
	public Blog(String blogTitle, String categoryName, String blogImage, String article, Long accountId) {
		this.blogTitle = blogTitle;
		this.categoryName = categoryName;
		this.blogImage = blogImage;
		this.article = article;
		this.accountId = accountId;
	}
	// getter setter
	// accountIdはAccountの中で設定したため
	// それ以外の物を設定する

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBlogImage() {
		return blogImage;
	}

	public void setBlogImage(String blogImage) {
		this.blogImage = blogImage;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

}
