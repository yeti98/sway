package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.manager.service.interfaces.ILecturerService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LecturerService implements ILecturerService {
    @Override
    public SwayClass getClassById(Long classId) {
        return null;
    }


    @Override
    public Page<SwayClass> getClassesByPage(Long page) {
        return null;
    }
}
