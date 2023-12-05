package com.mitocode.mitosales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.mitosales.dto.CategoryDTO;
import com.mitocode.mitosales.exception.ModelNotFoundException;
import com.mitocode.mitosales.model.Category;
import com.mitocode.mitosales.service.ICategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryService service;
    @MockBean(name = "categoryMapper")
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    Category CATEGORY_1 = new Category(1, "Electronics", "Electronic devices and gadgets", true);
    Category CATEGORY_2 = new Category(2, "Clothing", "Fashionable apparel for all seasons", true);
    Category CATEGORY_3 = new Category(3, "Books", "Literary works in various genres", true);


    CategoryDTO CATEGORY_DTO_1 = new CategoryDTO(1, "Electronics", "Electronic devices and gadgets", true);
    CategoryDTO CATEGORY_DTO_2 = new CategoryDTO(2, "Clothing", "Fashionable apparel for all seasons", true);
    CategoryDTO CATEGORY_DTO_3 = new CategoryDTO(3, "Books", "Literary works in various genres", true);



    @Test
    void readAllTest() throws Exception {
        List<Category> categories = List.of(CATEGORY_1, CATEGORY_2, CATEGORY_3);

        Mockito.when(service.readAll()).thenReturn(categories);
        Mockito.when(modelMapper.map(CATEGORY_1, CategoryDTO.class)).thenReturn(CATEGORY_DTO_1);
        Mockito.when(modelMapper.map(CATEGORY_2, CategoryDTO.class)).thenReturn(CATEGORY_DTO_2);
        Mockito.when(modelMapper.map(CATEGORY_3, CategoryDTO.class)).thenReturn(CATEGORY_DTO_3);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/categories")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].nameCategory", is("Clothing")));

    }

    @Test
    void readByIdTest() throws Exception {
        final  int ID = 1;
        Mockito.when(service.readById(any())).thenReturn(CATEGORY_1);
        Mockito.when(modelMapper.map(CATEGORY_1, CategoryDTO.class)).thenReturn(CATEGORY_DTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/categories/" + ID)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameCategory", is("Electronics")));
    }

    @Test
    void createTest() throws Exception {
        Mockito.when(service.save(any())).thenReturn(CATEGORY_3);
        Mockito.when(modelMapper.map(CATEGORY_3, CategoryDTO.class)).thenReturn(CATEGORY_DTO_3);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CATEGORY_DTO_3));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nameCategory", is("Books")))
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }

    @Test
    void updateTest() throws Exception {
        final int ID = 2;
        Mockito.when(service.update(any(), any())).thenReturn(CATEGORY_2);
        Mockito.when(modelMapper.map(CATEGORY_2, CategoryDTO.class)).thenReturn(CATEGORY_DTO_2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/categories/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CATEGORY_DTO_2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameCategory", is("Clothing")))
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }

    @Test
    void updateErrorTest() throws Exception {
        final int ID = 99;
        Mockito.when(service.update(any(), any())).thenThrow(new ModelNotFoundException("ID NOT VALID: " + ID));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/categories/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CATEGORY_DTO_2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect( result -> assertTrue(result.getResolvedException() instanceof  ModelNotFoundException));
    }

    @Test
    void deleteTest() throws Exception {
        final int ID = 1;
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/categories/" + ID)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteErrorTest() throws Exception {
        final int ID = 99;
        Mockito.doThrow(new ModelNotFoundException("ID NOT FOUND: " + ID)).when(service).delete(ID);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/categories/" + ID)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect( result -> assertTrue(result.getResolvedException() instanceof  ModelNotFoundException));

    }
}
