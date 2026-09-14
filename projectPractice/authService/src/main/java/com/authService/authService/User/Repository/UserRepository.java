package com.authService.authService.User.Repository;


import com.authService.authService.User.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);
    public User findByEmail(String email);
}
