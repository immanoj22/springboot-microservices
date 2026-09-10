package microservice.pratice.User.Repository;

import microservice.pratice.User.Models.RefreshToken;
import microservice.pratice.User.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {

    RefreshToken findByUser(User user);
    Optional<RefreshToken> findByRefreshToken(String token);
}
