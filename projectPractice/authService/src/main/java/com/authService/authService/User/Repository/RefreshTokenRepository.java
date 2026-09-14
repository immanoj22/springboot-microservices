package com.authService.authService.User.Repository;



import com.authService.authService.User.Models.RefreshToken;
import com.authService.authService.User.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {

    RefreshToken findByUser(User user);
    Optional<RefreshToken> findByRefreshToken(String token);
}
