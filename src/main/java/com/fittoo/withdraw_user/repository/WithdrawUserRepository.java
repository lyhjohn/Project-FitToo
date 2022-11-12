package com.fittoo.withdraw_user.repository;

import com.fittoo.withdraw_user.constant.WithdrawStatus;
import com.fittoo.withdraw_user.entity.WithdrawUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawUserRepository extends JpaRepository<WithdrawUser, String> {
}
