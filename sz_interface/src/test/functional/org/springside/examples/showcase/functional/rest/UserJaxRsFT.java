package org.system.zhrt.showcase.functional.rest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.system.zhrt.showcase.functional.BaseFunctionalTestCase;
import org.system.zhrt.showcase.webservice.rest.UserDTO;

/**
 * 对基于JAX-RS的实现Restful的测试
 * 
 * @author wangjx
 */
public class UserJaxRsFT extends BaseFunctionalTestCase {

	private static String resoureUrl;

	private RestTemplate restTemplate = new RestTemplate();

	@BeforeClass
	public static void initUrl() {
		resoureUrl = baseUrl + "/cxf/jaxrs/user";
	}

	@Test
	public void getUser() {
		UserDTO user = restTemplate.getForObject(resoureUrl + "/{id}.xml", UserDTO.class, 1L);
		assertEquals("admin", user.getLoginName());
		assertEquals("管理员", user.getName());
		assertEquals(new Long(1), user.getTeamId());

		user = restTemplate.getForObject(resoureUrl + "/{id}.json", UserDTO.class, 1L);
		assertEquals("admin", user.getLoginName());
		assertEquals("管理员", user.getName());
		assertEquals(new Long(1), user.getTeamId());
	}
}
