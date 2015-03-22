package com.diplom11.diplom11.UserTools;

import com.parse.ParseObject;

/**
 * Created by Mak on 19.03.2015.
 */
public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String midName;
    private String orgType;
    private Double sumReit;
    private Double voteCount;
    private String phone;
    private String userId;
    private String id;

    public User(ParseObject user) {
        email = (String) user.get("email");
        firstName = (String) user.get("firstName");
        lastName = (String) user.get("lastName");
        midName = (String) user.get("midName");
        orgType = (String) user.get("orgType");
        sumReit = Double.valueOf(user.get("sumReit").toString());
        voteCount = Double.valueOf(user.get("voteCount").toString());
        phone = (String) user.get("phone");
        userId = (String) user.get("userId");
        if (sumReit == null) {
            sumReit = 0.0;
        }
        if (voteCount == null) {
            voteCount = 0.0;
        }

        id = user.getObjectId();
    }

    public String toString() {
        return "ID: " + userId + "\n"
                + "e-mail: " + email + "\n"
                + "Фамилия: " + lastName + "\n"
                + "Имя: " + firstName + "\n"
                + "Отчество: " + midName + "\n"
                + "Тип организации: " + orgType + "\n"
                + "Телефон: " + phone + "\n"
                + "Рейтинг: " + getReiting() + "\n";
    }

    public String getReiting() {
        if (voteCount < 11) {
            return "новичок";
        }
        return (10 * (sumReit / voteCount)) / 10 + "/10";
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMidName() {
        return midName;
    }

    public String getOrgType() {
        return orgType;
    }

    public String getPhone(){
        return phone;
    }

    public String getUserId() {
        return userId;
    }
}
