package com.hoanghiep.perfume;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc	//test only the layer below applicatin
public class HttpRequestTest {
	
	//  test that assert the behavior of your application
//	@LocalServerPort
//	private int port;
//
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//	@Test
//	public void greetingShouldReturnDefaultMessage() throws Exception {
//		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
//				String.class)).contains("Hello, World");
//	}

	/*not start the server at all but to test only the layer below that, 
	where Spring handles the incoming HTTP request and hands it off to your controller. 
	That way, almost of the full stack is used, and your code will be called in exactly 
	the same way as if it were processing a real HTTP request but without the cost of 
	starting the server. To do that, use Spring’s MockMvc and ask for that to be injected for you
	 by using the @AutoConfigureMockMvc annotation on the test case. The following listing
	 */
	
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Test
//	public void shouldReturnDefaultMessage() throws Exception {
//		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
//				.andExpect(content().string(containsString("Hello, World")));
//	}
	//We can narrow the tests to only the web layer by using @WebMvcTest then Spring Boot instantiates only the web layer rather than the whole context
}
