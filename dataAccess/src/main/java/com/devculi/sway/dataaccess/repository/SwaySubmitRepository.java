package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwaySubmit;
import com.devculi.sway.dataaccess.entity.SwayUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface SwaySubmitRepository extends JpaRepository<SwaySubmit, Long> {
    SwaySubmit getFirstBySwayClassAndSwayUserOrderByCreatedAtDesc(SwayClass swayClass, SwayUser swayUser);
}
