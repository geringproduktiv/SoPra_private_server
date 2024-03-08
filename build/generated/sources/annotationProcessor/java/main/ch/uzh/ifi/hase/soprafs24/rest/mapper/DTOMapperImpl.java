package ch.uzh.ifi.hase.soprafs24.rest.mapper;

import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPutDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-08T21:33:48+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
public class DTOMapperImpl implements DTOMapper {

    @Override
    public User convertUserPostDTOtoEntity(UserPostDTO userPostDTO) {
        if ( userPostDTO == null ) {
            return null;
        }

        User user = new User();

        if ( userPostDTO.getBirthday() != null ) {
            user.setBirthday( DateTimeFormatter.ISO_LOCAL_DATE.format( userPostDTO.getBirthday() ) );
        }
        user.setPassword( userPostDTO.getPassword() );
        user.setName( userPostDTO.getName() );
        user.setCreation_date( userPostDTO.getCreation_date() );
        user.setUsername( userPostDTO.getUsername() );

        return user;
    }

    @Override
    public UserPostDTO convertEntityToUserPostDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserPostDTO userPostDTO = new UserPostDTO();

        if ( user.getBirthday() != null ) {
            userPostDTO.setBirthday( LocalDate.parse( user.getBirthday() ) );
        }
        userPostDTO.setPassword( user.getPassword() );
        userPostDTO.setName( user.getName() );
        userPostDTO.setCreation_date( user.getCreation_date() );
        userPostDTO.setId( user.getId() );
        userPostDTO.setUsername( user.getUsername() );
        if ( user.getStatus() != null ) {
            userPostDTO.setStatus( user.getStatus().name() );
        }
        userPostDTO.setToken( user.getToken() );

        return userPostDTO;
    }

    @Override
    public UserGetDTO convertEntityToUserGetDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserGetDTO userGetDTO = new UserGetDTO();

        if ( user.getBirthday() != null ) {
            userGetDTO.setBirthday( LocalDate.parse( user.getBirthday() ) );
        }
        userGetDTO.setPassword( user.getPassword() );
        userGetDTO.setName( user.getName() );
        userGetDTO.setId( user.getId() );
        userGetDTO.setCreation_date( user.getCreation_date() );
        userGetDTO.setUsername( user.getUsername() );
        userGetDTO.setToken( user.getToken() );
        userGetDTO.setStatus( user.getStatus() );

        return userGetDTO;
    }

    @Override
    public User convertUserPutDTOtoEntity(UserPutDTO userPutDTO) {
        if ( userPutDTO == null ) {
            return null;
        }

        User user = new User();

        if ( userPutDTO.getBirthday() != null ) {
            user.setBirthday( DateTimeFormatter.ISO_LOCAL_DATE.format( userPutDTO.getBirthday() ) );
        }
        user.setName( userPutDTO.getName() );
        user.setId( userPutDTO.getId() );
        user.setUsername( userPutDTO.getUsername() );

        return user;
    }

    @Override
    public UserPutDTO convertEntityToUserPutDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserPutDTO userPutDTO = new UserPutDTO();

        if ( user.getBirthday() != null ) {
            userPutDTO.setBirthday( LocalDate.parse( user.getBirthday() ) );
        }
        userPutDTO.setName( user.getName() );
        userPutDTO.setUsername( user.getUsername() );
        userPutDTO.setStatus( user.getStatus() );
        userPutDTO.setId( user.getId() );

        return userPutDTO;
    }
}
