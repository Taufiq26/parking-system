package com.example.parkingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.parkingsystem.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    
    @Query(nativeQuery = true, value = 
        "SELECT u.* FROM user AS u WHERE u.username=:username AND u.password=md5(:password)"
    )
    User doLogin(@Param("username") String username, @Param("password") String password);
}
