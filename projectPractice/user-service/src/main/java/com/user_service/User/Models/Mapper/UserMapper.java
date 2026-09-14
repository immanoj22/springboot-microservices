package com.user_service.User.Models.Mapper;


import com.user_service.User.Models.DTO.UserDTO;
import com.user_service.User.Models.User;


public class UserMapper {
    public User reuestToEntity(UserDTO userReuestdto){
        User user=new User();
        user.setEmail(userReuestdto.getEmail());
        user.setPassword(userReuestdto.getPassword());
        user.setRole(userReuestdto.getRole());
        user.setUserName(userReuestdto.getUserName());
        user.setProfileImage(userReuestdto.getProfileImage());

        return user;
    }

    public UserDTO EntityToResponse(User user){
        UserDTO userDto=new UserDTO();
        userDto.setEmail(user.getEmail());
        userDto.setStatus(user.getStatus());
        userDto.setUserName(user.getUserName());
        userDto.setProfileImage(user.getProfileImage());
        userDto.setRole(user.getRole());
        return userDto;
    }

}
