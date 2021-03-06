package co.com.viveres.susy.microserviceproduct.controller;

import static co.com.viveres.susy.microserviceproduct.DummyMock.productInputDto;
import static co.com.viveres.susy.microserviceproduct.DummyMock.productInputDtoUpdate;
import static co.com.viveres.susy.microserviceproduct.DummyMock.productOutputDto;
import static co.com.viveres.susy.microserviceproduct.DummyMock.productOutputDtoList;
import static co.com.viveres.susy.microserviceproduct.DummyMock.stockDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.viveres.susy.microservicecommons.dto.ProductDto;
import co.com.viveres.susy.microservicecommons.repository.IMessageRepository;
import co.com.viveres.susy.microserviceproduct.service.IProductService;

@WebMvcTest(ProductApiImpl.class)
class ProductApiImplTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private IProductService service;
	
	@MockBean
	private IMessageRepository messageRepository;
	
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() {
		this.objectMapper = new ObjectMapper();
	}
	
	@Test
	void createTest() throws JsonProcessingException, Exception {
		when(this.service.create(any(ProductDto.class))).thenReturn(productOutputDto());
		
		this.mvc.perform(post("/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(productInputDto())))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(this.objectMapper.writeValueAsString(productOutputDto())));
	}

	@Test
	void findAllTest() throws JsonProcessingException, Exception {
		
		List<ProductDto> productOutputDtoList = productOutputDtoList();
		
		when(this.service.findAll()).thenReturn(productOutputDtoList);
		
		this.mvc.perform(get("/v1/products")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", Matchers.hasSize(3)))
			.andExpect(content().json(this.objectMapper.writeValueAsString(productOutputDtoList)));
	}
	
	@Test
	void findByIdTest() throws JsonProcessingException, Exception {
		when(this.service.findById(anyLong())).thenReturn(productOutputDto());
		
		this.mvc.perform(get("/v1/products/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(this.objectMapper.writeValueAsString(productOutputDto())));
	}
	
	@Test	
	void updateTest() throws JsonProcessingException, Exception {
		this.mvc.perform(put("/v1/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(productInputDtoUpdate())))
			.andExpect(status().isOk());
	}
	
	@Test	
	void stockManagementByProductTest() throws JsonProcessingException, Exception {
		this.mvc.perform(put("/v1/products/1/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(stockDto("add"))))
			.andExpect(status().isOk());
	}

}
