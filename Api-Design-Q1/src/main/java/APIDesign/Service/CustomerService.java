package APIDesign.Service;

import APIDesign.Entity.Customer;
import APIDesign.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void getCustomerDetail(int id){
        Customer customer  = customerRepository.findById(id).orElse(null);
        System.out.println(Hibernate.isInitialized(customer.getOrders()));
        System.out.println(customer);
    }
}
