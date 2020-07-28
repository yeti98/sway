package com.devculi.sway.business.shared.utils;

import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.sharedmodel.model.UserModel;
import org.modelmapper.ModelMapper;

public final class Entity2DTO {
    private static ModelMapper modelMapper = new ModelMapper();

    public static UserModel user2DTO(SwayUser user) {
        return modelMapper.map(user, UserModel.class);
    }
}
