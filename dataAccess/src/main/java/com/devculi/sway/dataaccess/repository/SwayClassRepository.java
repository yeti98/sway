package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.SwayClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface SwayClassRepository extends JpaRepository<SwayClass, Long> {
}
