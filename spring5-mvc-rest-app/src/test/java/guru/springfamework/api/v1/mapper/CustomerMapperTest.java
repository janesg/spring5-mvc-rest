package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final String FIRST_NAME = "Bob";
    public static final String LAST_NAME = "Ajob";
    public static final Long ID = 12L;

    @Test
    public void customerToCustomerDTO() {

        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setId(ID);

        CustomerDTO dto = CustomerMapper.INSTANCE.customerToCustomerDTO(customer);

        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
    }

    @Test
    public void customerDTOToCustomer() {

        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);

        Customer customer = CustomerMapper.INSTANCE.customerDTOToCustomer(dto);

        assertEquals(FIRST_NAME, customer.getFirstName());
        assertEquals(LAST_NAME, customer.getLastName());
    }
}