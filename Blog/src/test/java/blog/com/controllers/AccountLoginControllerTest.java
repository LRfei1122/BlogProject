package blog.com.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MvcResult;

import blog.com.models.entity.Account;
import blog.com.services.AccountService;
import jakarta.servlet.http.HttpSession;

@SpringBootTest
//これはSpringBootで動くテスト
@AutoConfigureMockMvc
//自動的にMockMvcを使用
public class AccountLoginControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	// サービスクラスを使ったデータ作成
	public void prepareData() {
		// ユーザーの情報を作成する（Entityの内容を返すので）
		Account LRfei = new Account("LRfei", "test@test.com", "1234");
		// ログインが成功： email "test@test.com"、 password "1234" true
		when(accountService.loginCheck("test@test.com", "1234")).thenReturn(LRfei);
		// ログインが失敗： email "fail@test.com" 、 間違ったパスワード(1234abcd) false
		when(accountService.loginCheck(any(), any())).thenReturn(null);
	}

	// ログインページを正しく取得するテスト
	// "/account/login" へのGETリクエストを作成し、実行する。
	@Test
	public void testGetLoginPage_succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/account/login");
		mockMvc.perform(request).andExpect(view().name("login.html"));
	}

	// email入力値テスト
	/*
	 * 正しいメールアドレス(test@test.com)及び正しいパスワード(1234)を入力
	 * "/account/login/process"へのPOSTリクエストを作成し、実行する
	 */
	@Test
	public void testAccountLogin_Succeed() throws Exception {
		// テスト用のMockHttpSessionを作成
		MockHttpSession session = new MockHttpSession();
		// セッションの設定
		Account account = new Account();
		account.setAccountId(1L);
		account.setAccountName("LRfei");
		account.setAccountEmail("test@test.com");
		account.setPassword("1234");
		session.setAttribute("account", account);

		RequestBuilder request = MockMvcRequestBuilders.post("/account/login/process").param("email", "test@test.com")
				.param("password", "1234").session(session);
		// "test@test.com", "1234"
		// ログインが成功したら「blog-list.html」に遷移して、入力された値が渡されているかのテスト

		MvcResult result = mockMvc.perform(request).andExpect(redirectedUrl("/blog/list")).andReturn();
		// セッションから "admin" を取得して null ではないことを確認
		HttpSession sessionAfterRequest = result.getRequest().getSession();
		Object adminInSession = sessionAfterRequest.getAttribute("account");
		assertNotNull(adminInSession, "sessionはNULLではない");
	}

	/*
	 * 間違ったメールアドレス(fail@test.com)及び正しいパスワード(1234)を入力
	 * "/account/login/process"へのPOSTリクエストを作成し、実行する
	 */
	@Test
	public void testAccountLogin_emailfalseAndPasswordTrue() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/account/login/process").param("email", "test@test.com")
				.param("password", "1234");
		mockMvc.perform(request).andExpect(view().name("login.html"));
	}

	// password入力値テスト
	/*
	 * 正しいメールアドレス(test@test.com)及び間違ったパスワード(1234abcd)を入力
	 * /account/login/process"へのPOSTリクエストを作成し、実行する
	 */
	@Test
	public void testAccountLogin_emailTrueAndPasswordfalse() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/account/login/process").param("email", "test@test.com")
				.param("password", "1234abcd");
		mockMvc.perform(request).andExpect(view().name("login.html"));
	}

	/*
	 * 間違ったメールアドレス(fail@test.com)及び間違ったパスワード(1234abcd)を入力
	 * "/account/login/process"へのPOSTリクエストを作成し、実行する
	 */
	@Test
	public void testAccountLogin_emailfalseAndPasswordfalse() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/account/login/process").param("email", "fail@test.com")
				.param("password", "1234abcd");
		mockMvc.perform(request).andExpect(view().name("login.html"));
	}

}
