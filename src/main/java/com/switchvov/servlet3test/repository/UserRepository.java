package com.switchvov.servlet3test.repository;

import com.switchvov.servlet3test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Switch
 * @Date 2018/1/21
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
