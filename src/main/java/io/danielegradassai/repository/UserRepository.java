package io.danielegradassai.repository;

import io.danielegradassai.entity.Role;
import io.danielegradassai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    @Query("SELECT u.roles FROM User u WHERE u.id = :id")
    List<Role> findRolesByUserId(@Param(value = "id") Long id);
    List<User> findByRolesAuthority(String authority);
    List<User> findBySubscriptionTrue();
}
