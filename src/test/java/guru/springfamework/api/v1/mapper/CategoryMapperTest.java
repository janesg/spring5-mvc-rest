package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryMapperTest {

    private static final String NAME = "Joe";
    private static final long ID = 12L;

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {

        // given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        // when
        CategoryDTO dto = categoryMapper.categoryToCategoryDTO(category);

        // then
        assertEquals(ID, dto.getId().longValue());
        assertEquals(NAME, dto.getName());

    }
}