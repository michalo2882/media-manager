package mediamanager.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class DbOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "optionKey", unique = true)
    String key;
    @Column(name = "optionValue")
    String value;
}
