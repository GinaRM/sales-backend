package com.mitocode.mitosales.service;

import com.mitocode.mitosales.model.Category;
import com.mitocode.mitosales.repo.ICategoryRepo;
import com.mitocode.mitosales.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @MockBean
    private CategoryServiceImpl service;

    @MockBean
    private ICategoryRepo repo;

    private Category CATEGORY_1;
    private Category CATEGORY_2;
    private Category CATEGORY_3;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.service = new CategoryServiceImpl(repo);

        CATEGORY_1 = new Category(1, "Electronics", "Electronic devices and gadgets", true);
        CATEGORY_2 = new Category(2, "Clothing", "Fashionable apparel for all seasons", true);
        CATEGORY_3 = new Category(3, "Books", "Literary works in various genres", true);

        List<Category> categories = List.of(CATEGORY_1, CATEGORY_2, CATEGORY_3);
        Mockito.when(repo.findAll()).thenReturn(categories);
        Mockito.when(repo.findById(any())).thenReturn(Optional.of(CATEGORY_1));
        Mockito.when(repo.save(any())).thenReturn(CATEGORY_1);
    }

    @Test
    void readAllTest() throws Exception {

        List<Category> response =  service.readAll();
        assertNotNull(response);
        assertEquals(response.size(), 3);
    }
    @Test
    void readByIdTest() throws Exception {
        final int ID = 1;
        Category response = service.readById(ID);
        assertNotNull(response);
    }

    @Test
    void saveTest() throws Exception {
        Category response = service.save(CATEGORY_1);
        assertNotNull(response);
    }

    @Test
    void updateTest() throws Exception {

        Category response = service.update(CATEGORY_1, CATEGORY_1.getIdCategory());
        assertNotNull(response);
    }

    @Test
    void deleteTest() throws Exception {
        final int ID = 1;
        repo.deleteById(ID);

        verify(repo, times(1)).deleteById(ID);
    }
}
