package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Inheritance(strategy = InheritanceType.JOINED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Operation> operations = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categories categories;

    @Column(nullable = false)
    private String description;

    public enum Categories {
        INCOME,
        EXPENSE;
    }

    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }
}
