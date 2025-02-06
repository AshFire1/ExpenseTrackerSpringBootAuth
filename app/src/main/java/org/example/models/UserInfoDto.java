package org.example.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.example.entities.UserInfo;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDto extends UserInfo
{
    private String userName; //first_name

    private String lastName; // last_name

    private Long phoneNumber;

    private String email; //email
}
