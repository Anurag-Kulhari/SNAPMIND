package org.example;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserDetail,Long>{

    Optional<UserDetail> findByPhone(String phone);


}
