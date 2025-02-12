package es.ua.biblioteca.repository;

import es.ua.biblioteca.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    @Query("select b from Book b Where b.title like %:title%")
    List<Book> findByTitle(@Param("title") String title);
}
