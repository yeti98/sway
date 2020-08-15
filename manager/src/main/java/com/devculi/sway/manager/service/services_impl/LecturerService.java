package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.manager.service.interfaces.ILecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LecturerService implements ILecturerService {
    @Autowired
    SwayTestService testService;
    @Override
    public SwayClass getClassById(Long classId) {
        return null;
    }


    @Override
    public Page<SwayClass> getClassesByPage(Long page) {
        return null;
    }

    @Override
    public Page<SwayTest> getHomeworkByPage(Integer page){
        return testService.getHomeworkByPage(page);
    }

    public List<SwayTest> searchByTestId(String keyword) {
        return null;
    }
}
