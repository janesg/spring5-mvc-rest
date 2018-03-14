package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Data Loaded : Category count = " + categoryRepository.count());
    }

    private void loadCustomers() {
        Customer derek = new Customer(null, "Derek", "Domino");
        Customer phil = new Customer(null, "Phil", "Taylor");
        Customer finbar = new Customer(null, "Finbar", "Saunders");
        Customer nancy = new Customer(null, "Nancy", "Drew");

        customerRepository.save(derek);
        customerRepository.save(phil);
        customerRepository.save(finbar);
        customerRepository.save(nancy);

        System.out.println("Data Loaded : Customer count = " + customerRepository.count());
    }
}