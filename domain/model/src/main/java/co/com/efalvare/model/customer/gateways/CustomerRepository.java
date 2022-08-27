package co.com.efalvare.model.customer.gateways;

import co.com.efalvare.model.customer.Customer;
import reactor.core.publisher.Mono;

public interface CustomerRepository {

    Mono<Customer> createCustomer(Customer customer);

}
