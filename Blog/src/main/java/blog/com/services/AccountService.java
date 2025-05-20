package blog.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.AccountDao;
import blog.com.models.entity.Account;

//User-Controller-"Service"-Dao-teble
//サービス層（Service）：DAO層をくみあわせて使用し、controllerへ利用可能なserviceを提供。
@Service
public class AccountService {
	// AdminDao呼び出す
	@Autowired
	private AccountDao accountDao;

	// 新account作成
	public boolean createAccount(String accountName, String accountEmail, String password) {
		// もし、findByAdminEmail(adminEmail) == nullだったら登録処理
		if (accountDao.findByAccountEmail(accountEmail) == null) {
			// saveメソッドを使用し、登録処理
			accountDao.save(new Account(accountName, accountEmail, password));
			// boolean式のtrue 保存できる
			return true;
		} else {
			// 保存できない false
			return false;
		}
	}
	
	// login処理
	public Account loginCheck(String accountEmail, String password) {
		/*
		 * userはメールとpasswordを入力、
		 * デーブルの中で確認する
		 * もしaccountがない、null
		 */
		Account account = accountDao.findByAccountEmailAndPassword(accountEmail, password);
		if (account == null) {
			return null;
		} else {
			return account;
		}
	}

}
