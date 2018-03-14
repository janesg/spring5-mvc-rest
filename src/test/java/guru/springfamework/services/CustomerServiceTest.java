package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    private static final Long ID = 12L;
    private static final String URI = "/api/v1/customers";
    private static final String FIRST_NAME = "Bob";
    private static final String LAST_NAME = "Bobbins";
    private static final String CUSTOMER_URL = URI + "/" + ID;

    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void getAllCustomers() {

        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> dtos = customerService.getAllCustomers();

        assertEquals(3, dtos.size());
    }

    @Test
    public void getCustomerById() {

        Customer bob = new Customer(ID, FIRST_NAME, LAST_NAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(bob));

        CustomerDTO dto = customerService.getCustomerById(ID);

        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
    }

    @Test
    public void createNewCustomer() {

        Customer customer = new Customer(ID, FIRST_NAME, LAST_NAME);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO dto = customerService.createNewCustomer(new CustomerDTO());

        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
        assertEquals(CUSTOMER_URL, dto.getCustomerUrl());
    }

    @Test
    public void updateCustomer() {

        Customer customer = new Customer(ID, FIRST_NAME, LAST_NAME);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO dto = customerService.updateCustomer(ID, new CustomerDTO());

        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
        assertEquals(CUSTOMER_URL, dto.getCustomerUrl());
    }

}