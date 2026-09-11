package microservice.pratice.Quiz.Repository;

import microservice.pratice.Quiz.Model.QuizCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizCategoryRepository extends JpaRepository<QuizCategory,Integer> {

    @Query("select distinct q from QuizCategory q " +
            "left join fetch q.quizQuestion " +
            "where lower(q.category) = lower(:category)")
    List<QuizCategory> findAllDetailsByCategory(@Param("category") String category);
}
