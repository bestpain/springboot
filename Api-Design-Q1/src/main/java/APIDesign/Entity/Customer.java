package APIDesign.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Customer {
    @Id
    private int id;

    private String name;

    private String email;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

