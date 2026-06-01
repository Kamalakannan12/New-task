package com.example.ecommerce.configuration;
import com.example.ecommerce.entity.UserDetails;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.time.LocalDate;


@Component
public class UserFieldSetMapper implements FieldSetMapper<UserDetails> {

    @Override
    public UserDetails mapFieldSet(FieldSet fieldSet) throws BindException {

        UserDetails user = new UserDetails();

        user.setAddress(fieldSet.readString("address"));
        user.setEmail(fieldSet.readString("email"));
        user.setName(fieldSet.readString("name"));
        user.setPhone(fieldSet.readString("phone"));
        user.setPassword(fieldSet.readString("password"));

        // current date
        user.setLastactive(LocalDate.now());
        System.out.println("READING ROW : " + user.getEmail());
        return user;
    }}
