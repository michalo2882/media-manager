package mediamanager.repository;

import mediamanager.model.DbOption;
import org.springframework.data.repository.CrudRepository;

public interface DbOptionRepository extends CrudRepository<DbOption, Long> {

    DbOption findByKey(String key);
}
