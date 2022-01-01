package com.uber.software.repositories;

<<<<<<< HEAD
import com.uber.software.enums.UserRole;
=======
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec
import com.uber.software.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    List<AppUser> findByRole(UserRole userRole);
=======
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec
}
