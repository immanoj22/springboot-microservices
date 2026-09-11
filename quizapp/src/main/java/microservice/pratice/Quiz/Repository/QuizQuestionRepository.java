package microservice.pratice.Quiz.Repository;

import microservice.pratice.Quiz.Model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Integer> {
    @Query("select distinct q from QuizQuestion q " +
            "left join fetch q.answers " +
            "left join fetch q.category")
    List<QuizQuestion> findAllDetails();

}
