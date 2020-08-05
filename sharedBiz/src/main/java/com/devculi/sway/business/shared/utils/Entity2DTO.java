package com.devculi.sway.business.shared.utils;

import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.sharedmodel.model.UserModel;
import org.modelmapper.ModelMapper;

public final class Entity2DTO {
    private static ModelMapper modelMapper = new ModelMapper();

    public static UserModel user2DTO(SwayUser user) {
        return modelMapper.map(user, UserModel.class);
    }

    public static SwayClassModel class2DTO(SwayClass swayClass) {
        return modelMapper.map(swayClass, SwayClassModel.class);
    }
}
