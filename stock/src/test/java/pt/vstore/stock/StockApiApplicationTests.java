package pt.vstore.stock;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class StockApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;


	@Test
	public void getAllProducts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/list")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	@Test
	public void getProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/430769e6-c6ed-4dd8-b52c-9bc997fcfa43")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	@Test
	public void notFoundForInvalidProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/00000000-0000-0000-0000-000000000000")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();
	}
	@Test
	public void postNewProduct() throws Exception {
		String prod = "{\"name\":\"prod\",\"description\":\"prod desc\",\"available\":10,\"price\":1.99}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(prod)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}

}
