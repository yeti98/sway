package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface IClassService {
    Page<SwayClass> getClassByPage(Pageable pageable);
}
