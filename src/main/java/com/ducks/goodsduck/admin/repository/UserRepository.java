package com.ducks.goodsduck.admin.repository;

import com.ducks.goodsduck.admin.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findById(Long userId, Pageable pageable);

//    @Query("select u from User u where u.nickName like %:nickName%")
    List<User> findByNickName(String nickName, Pageable pageable);

//    @Query("select u from User u where u.email like %:email%")
    List<User> findByEmail(String email, Pageable pageable);

//    @Query("select u from User u where u.phoneNumber like %:phoneNumber%")
    List<User> findByPhoneNumber(String phoneNumber, Pageable pageable);
}
