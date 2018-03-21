package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.CategoryService;
import guru.springfamework.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    private static final String URI = "/api/v1/categories";
    private static final String NAME = "Joe";
    private static final Long ID = 12L;

    @InjectMocks
    private CategoryController controller;

    @Mock
    private CategoryService service;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                    .setControllerAdvice(new RestResponseEntityExceptionHandler())
                    .build();
    }

    @Test
    public void testListCategories() throws Exception {

        List<CategoryDTO> dtos = Arrays.asList(new CategoryDTO(), new CategoryDTO(), new CategoryDTO());

        when(service.getAllCategories()).thenReturn(dtos);

        mockMvc.perform(get(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(3)));
    }

    @Test
    public void testGetCategoryByName() throws Exception {

        CategoryDTO dto = new CategoryDTO();
        dto.setId(ID);
        dto.setName(NAME);

        when(service.getCategoryByName(anyString())).thenReturn(dto);

        mockMvc.perform(get(URI + "/" + NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID.intValue())))
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void testGetByNameNotFound() throws Exception {

        when(service.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(URI + "/Foo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}