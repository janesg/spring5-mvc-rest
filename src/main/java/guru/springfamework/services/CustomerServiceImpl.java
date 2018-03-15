package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final String URI = "/api/v1/customers/";

    private CustomerRepository repository;
    private CustomerMapper mapper;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return repository.findAll().stream()
                .map(customer -> {
                    CustomerDTO dto = mapper.customerToCustomerDTO(customer);
                    dto.setCustomerUrl(URI + customer.getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> optCustomer = repository.findById(id);

        if (optCustomer.isPresent()) {
            CustomerDTO dto = mapper.customerToCustomerDTO(optCustomer.get());
            dto.setCustomerUrl(URI + id);
            return dto;
        } else {
            throw new EntityNotFoundException("Customer not found : id = " + id);
        }
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

        return saveCustomer(mapper.customerDTOToCustomer(customerDTO));
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {

        Customer customer = mapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);

        return saveCustomer(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {

        Optional<Customer> optCust = repository.findById(id);

        if (optCust.isPresent()) {
            Customer existing = optCust.get();

            if (customerDTO.getFirstName() != null) {
                existing.setFirstName(customerDTO.getFirstName());
            }

            if (customerDTO.getLastName() != null) {
                existing.setLastName(customerDTO.getLastName());
            }

            return mapper.customerToCustomerDTO(repository.save(existing));

        } else {
            throw new EntityNotFoundException("Customer not found : id = " + id);
        }
    }

    private CustomerDTO saveCustomer(Customer customer) {
        Customer savedCustomer = repository.save(customer);

        CustomerDTO dto = mapper.customerToCustomerDTO(savedCustomer);
        dto.setCustomerUrl(URI + savedCustomer.getId());

        return dto;
    }

}
