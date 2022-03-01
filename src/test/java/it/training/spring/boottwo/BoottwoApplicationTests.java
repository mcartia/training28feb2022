package it.training.spring.boottwo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@WebMvcTest
@AutoConfigureMockMvc
class BoottwoApplicationTests {

	@Autowired
	ApplicationContext ctx;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void helloTest() {
		for (String bean : ctx.getBeanDefinitionNames()){
			System.out.println("> "+bean);
		}
		assert (ctx!=null);
	}

	@Test
	void apiTest() throws Exception {
		mockMvc.perform(
				get("/api/people/{id}", 1L)
						.contentType("application/json"))
				.andDo(print()).andExpect(status().isOk());
	}

}
