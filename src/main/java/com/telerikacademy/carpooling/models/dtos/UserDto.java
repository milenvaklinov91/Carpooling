package com.telerikacademy.carpooling.models.dtos;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

    public class UserDto {
        @NotNull(message = "Username can't be empty")
        @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols")
        @NotBlank
        private String username;
        @NotBlank
        @Size(min = 8)
        private String password;
        @NotNull(message = "Name can't be empty")
        @Size(min = 2, max = 20, message = "Name should be between 2 and 20 symbols")
        @NotBlank
        private String firstName;
        @NotNull(message = "Last name can't be empty")
        @Size(min = 2, max = 20, message = "Last name should be between 2 and 20 symbols")
        @NotBlank
        private String lastName;
        @NotNull(message = "Email can't be empty")
        @Email(message = "Email is invalid")
        @NotBlank
        @Valid
        private String email;
        /*private String profilePic="static/images/profile.jpg";*/

        private int phone_number;


        public UserDto() {
        }

        public int getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(int phone_number) {
            this.phone_number = phone_number;
        }


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


/*        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }*/
    }


