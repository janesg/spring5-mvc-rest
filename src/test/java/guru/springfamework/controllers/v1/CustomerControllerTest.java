package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class CustomerControllerTest {

    private static final String URI = "/api/v1/customers";
    private static final Long ID = 12L;
    private static final String FIRST_NAME = "Bob";
    private static final String LAST_NAME = "Bobbins";
    private static final String CUSTOMER_URL = URI + "/" + ID;

    @InjectMocks
    private CustomerController controller;

    @Mock
    private CustomerService service;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllCustomers() throws Exception {

        List<CustomerDTO> dtos = Arrays.asList(new CustomerDTO(), new CustomerDTO(), new CustomerDTO());

        when(service.getAllCustomers()).thenReturn(dtos);

        mockMvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)));
    }

    @Test
    public void getCustomerById() throws Exception {

        CustomerDTO dto = new CustomerDTO(FIRST_NAME, LAST_NAME, CUSTOMER_URL);

        when(service.getCustomerById(anyLong())).thenReturn(dto);

        mockMvc.perform(get(CUSTOMER_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void createNewCustomer() throws Exception {

        CustomerDTO dto = new CustomerDTO(FIRST_NAME, LAST_NAME, CUSTOMER_URL);

        when(service.createNewCustomer(any(CustomerDTO.class))).thenReturn(dto);

        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void updateCustomer() throws Exception {

        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);

        CustomerDTO savedDto = new CustomerDTO(FIRST_NAME, LAST_NAME, CUSTOMER_URL);

        when(service.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(savedDto);

        mockMvc.perform(put(CUSTOMER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void patchCustomer() throws Exception {

        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(FIRST_NAME);

        CustomerDTO savedDto = new CustomerDTO(FIRST_NAME, LAST_NAME, CUSTOMER_URL);

        when(service.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(savedDto);

        mockMvc.perform(patch(CUSTOMER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void deleteCustomer() throws Exception {

        // Mocking a method that returns void
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Stream.of(invocation.getArguments()).forEach(arg -> log.debug("Arg = " + arg.toString()));
                return null;
            }
        }).when(service).deleteCustomer(anyLong());

        mockMvc.perform(delete(CUSTOMER_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).deleteCustomer(anyLong());
    }

}