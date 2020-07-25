package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Integer> {
}
