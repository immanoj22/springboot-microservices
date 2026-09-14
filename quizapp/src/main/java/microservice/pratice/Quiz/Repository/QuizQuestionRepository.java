package microservice.pratice.Quiz.Repository;

import microservice.pratice.Quiz.Model.QuizQuestion;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Integer> {
    @Query("select distinct q from QuizQuestion q " +
            "left join fetch q.answers " +
            "left join fetch q.category")
    List<QuizQuestion> findAllDetails();

    @Query("SELECT DISTINCT q FROM QuizQuestion q " +
            "LEFT JOIN FETCH q.answers " +
            "LEFT JOIN FETCH q.category c " +
            "WHERE lower(c.category) = lower(?1)")
    List<QuizQuestion> findWithCategory(String category);

    @Query(
            "SELECT COUNT(q) FROM QuizQuestion q " +
                    "LEFT JOIN q.category c " +
                    "WHERE LOWER(c.category) = LOWER(?1)"
    )
    int getCategoryCount(String cat);

    @Query("""
    SELECT q
    FROM QuizQuestion q
    Left JOIN q.category c
    WHERE LOWER(c.category) = LOWER(?1)
    """)
    List<QuizQuestion> findWithCategory(
            String category,
            Pageable pageable
    );
}
