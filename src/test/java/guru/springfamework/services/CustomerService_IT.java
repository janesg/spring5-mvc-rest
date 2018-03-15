package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerService_IT {

    private static final String UPDATED_NAME = "UpdatedName";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        // Note: ID's of created entities will be different for each test because
        //       even though DB changes are rolled back the sequence does not reset
        // https://stackoverflow.com/questions/19470700/reset-jpa-generated-value-between-tests
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerFirstName() {

        Long id = getExistingCustomerId();

        Optional<Customer> optOriginalCustomer = customerRepository.findById(id);
        assertTrue(optOriginalCustomer.isPresent());
        Customer originalCustomer = optOriginalCustomer.get();

        //store original first and last name
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(UPDATED_NAME);

        customerService.patchCustomer(id, customerDTO);

        Optional<Customer> optUpdatedCustomer = customerRepository.findById(id);
        assertTrue(optUpdatedCustomer.isPresent());
        Customer updatedCustomer = optUpdatedCustomer.get();

        assertEquals(UPDATED_NAME, updatedCustomer.getFirstName());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));
    }

    @Test
    public void patchCustomerLastName() {

        Long id = getExistingCustomerId();

        Optional<Customer> optOriginalCustomer = customerRepository.findById(id);
        assertTrue(optOriginalCustomer.isPresent());
        Customer originalCustomer = optOriginalCustomer.get();

        //store original first and last name
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(UPDATED_NAME);

        customerService.patchCustomer(id, customerDTO);

        Optional<Customer> optUpdatedCustomer = customerRepository.findById(id);
        assertTrue(optUpdatedCustomer.isPresent());
        Customer updatedCustomer = optUpdatedCustomer.get();

        assertEquals(UPDATED_NAME, updatedCustomer.getLastName());
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstName()));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
    }

    @Test
    public void deleteCustomer() {

        Long id = getExistingCustomerId();

        Optional<Customer> optOriginalCustomer = customerRepository.findById(id);
        assertTrue(optOriginalCustomer.isPresent());

        customerService.deleteCustomer(id);

        Optional<Customer> optDeletedCustomer = customerRepository.findById(id);
        assertFalse(optDeletedCustomer.isPresent());
    }

    private Long getExistingCustomerId() {
        return customerRepository.findAll().stream()
                .findAny().orElseThrow(EntityNotFoundException::new).getId();
    }
}
