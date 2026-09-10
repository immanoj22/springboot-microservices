package microservice.pratice.Quiz.Repository;

import microservice.pratice.Quiz.Model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Integer> {
}
