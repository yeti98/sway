package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.SwayClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SwayClassRepository extends JpaRepository<SwayClass, Long> {
    @Query("SELECT cl FROM SwayClass cl WHERE cl.classId LIKE :keyword")
    List<SwayClass> findByClassIdLike(@Param("keyword") String keyword);
}
