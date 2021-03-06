package klimczuk.kacper.simplecrud.repositories;

import klimczuk.kacper.simplecrud.models.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
