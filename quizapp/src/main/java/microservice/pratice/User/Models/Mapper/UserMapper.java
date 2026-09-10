package microservice.pratice.User.Models.Mapper;


import microservice.pratice.User.Models.DTO.UserDTO;
import microservice.pratice.User.Models.User;

public class UserMapper {
    public User reuestToEntity(UserDTO userReuestdto){
        User user=new User();
        user.setEmail(userReuestdto.getEmail());
        user.setPassword(userReuestdto.getPassword());
        user.setRole(userReuestdto.getRole());
        return user;
    }

    public UserDTO EntityToResponse(User user){
        UserDTO userDto=new UserDTO();
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
