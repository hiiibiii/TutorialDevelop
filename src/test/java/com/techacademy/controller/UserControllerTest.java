package com.techacademy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {
	private MockMvc mockMvc;

	private final WebApplicationContext webApplicationContext;

	UserControllerTest(WebApplicationContext context){
		this.webApplicationContext = context;
	}

	@BeforeEach
		void beforeEach() {
		// Spring Securityを有効にする
		mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
		}

	@Test
	@DisplayName("User更新画面")
	@WithMockUser
	void testGetUser() throws Exception {
		// HTTPリクエストに対するレスポンスの検証
		MvcResult result = mockMvc.perform(get("/user/update/1/"))// URLにアクセス
				.andExpect(status().isOk())// ステータスを確認[200OK]
				.andExpect(model().attributeExists("user")) // Modelの内容を確認
				.andExpect(model().hasNoErrors())// Modelのエラー有無の確認
				.andExpect(view().name("user/update"))// viewの名称確認
				.andReturn(); //結果を返却
		//Userの検証 userを取り出して検証
		User user = (User)result.getModelAndView().getModel().get("user");
		assertEquals(1, user.getId()); //下記コードの引数と値が等しいか判定
		assertEquals("キラメキ太郎", user.getName()); //期待値
	}
//課題
	@Test
	@DisplayName("User一覧画面")
	@WithMockUser
	void testGetList() throws Exception{
		MvcResult result = mockMvc.perform(get("/user/list"))// URLにアクセス
				.andExpect(status().isOk())// HTTPステータスが200OKであること
				.andExpect(model().attributeExists("userlist")) // Modelにuserlistが含まれていること
				.andExpect(model().hasNoErrors())// Modelにエラーが無いこと
				.andExpect(view().name("user/list"))// viewの名前が user/list であること
				.andReturn(); //結果を返却

		List<User> userlist = (List<User>)result.getModelAndView().getModel().get("userlist");//Modelからuserlistを取り出す
		assertEquals(3, userlist.size());	//件数が3件であること

		//userlistから1件ずつ取り出し、idとnameを検証する
//		assertEquals(1,userlist.get(0).getId());//期待値.リストからユーザを取得.IDを指定
//		assertEquals("キラメキ太郎", userlist.get(0));
		User user1 = userlist.get(0);
		assertEquals(1,user1.getId());
		assertEquals("キラメキ太郎",user1.getName());
		User user2 = userlist.get(1);
		assertEquals(2,user2.getId());
		assertEquals("キラメキ次郎", user2.getName());
		User user3 = userlist.get(2);
		assertEquals(3,user3.getId());
		assertEquals("キラメキ花子", user3.getName());
	}
}