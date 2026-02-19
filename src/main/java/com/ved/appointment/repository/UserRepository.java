package com.ved.appointment.repository;
import com.ved.appointment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository  extends JpaRepository<User,Long> {
//optional karne ka reason yah hai ki value mil bhi sakti nii bhi mil sakti to null pointer exception aane ka laoad nii rhega
    //findbymail aur bhi bhut yah sab derived query method hai jo spring jpa internally parse karke query bna deta hai
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
