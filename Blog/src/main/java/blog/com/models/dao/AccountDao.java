package blog.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.com.models.entity.Account;

public interface AccountDao extends JpaRepository<Account, Long> {
	// account 保存 更新
	Account save(Account account);

	// Accountの登録する時、同じmailがあると登録できない
	Account findByAccountEmail(String accountEmail);

	// login処理に使用 入力したmailとpasswordが一致している時だけdataを取得する
	Account findByAccountEmailAndPassword(String accountEmail, String accountPassword);
}
