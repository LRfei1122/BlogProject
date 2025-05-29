package blog.com.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blog.com.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountRegisterControllerTest {
	@Autowired
	private MockMvc mockMvc;

	// AccountServiceの偽
	@MockBean
	private AccountService accountService;

	// サービスクラスを使ったデータ作成
	@BeforeEach
	public void prepareData() {
		// 登録できる場合 name(LRfei)、mail(test@test.com)とpassword(1234)を入力
		when(accountService.createAccount("LRfei", "test@test.com", "1234")).thenReturn(true);
		// ログインが失敗 name（Fail）、すでに登録されているmail(test@test.com)とpassword(1234)を入力
		when(accountService.createAccount("Fail", "test@test.com", "1234")).thenReturn(false);
	}

	// "/account/register" へのGETリクエストを作成し、実行する。
	@Test
	public void testGetAccountRegisterPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/account/register");
		mockMvc.perform(request).andExpect(view().name("register.html"));

	}

	// 登録処理 @PostMapping("/account/register/process")
	/*
	 * 登録が成功するかのテスト ユーザー名(LRfei)、メールアドレス(test@test.com)及びパスワード(1234)を入力
	 * /account/register/process" へのPOSTリクエストを作成し、実行する
	 */
	@Test
	public void testAccountRegister_NewAccount_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/account/register/process").param("userName", "LRfei")
				.param("email", "test@test.com").param("password", "1234");
		mockMvc.perform(request).andExpect(view().name("login.html"));
		// Verify 指定された引数で1回だけ呼び出されたことを確認
		verify(accountService, times(1)).createAccount("LRfei", "test@test.com", "1234");
	}

	/*
	 * 登録が失敗するかのテスト ユーザー名（Fail）、すでに登録されているメールアドレス(test@test.com)及びパスワード(1234)を入力
	 * /account/register/process" へのPOSTリクエストを作成し、実行する
	 */
	@Test
	public void testAccountRegister_NewAccount_Fail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/account/register/process").param("userName", "Fail")
				.param("email", "test@test.com").param("password", "1234");
		mockMvc.perform(request).andExpect(view().name("register.html"));
	}

}
