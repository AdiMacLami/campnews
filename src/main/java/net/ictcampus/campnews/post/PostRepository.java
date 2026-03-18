package net.ictcampus.campnews.post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//Crud = Spring Data JPA
//Spring Framework
@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findAll();
}
