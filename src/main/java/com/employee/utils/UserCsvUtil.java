package com.employee.utils;

/**
 * @author satyakaveti
 */

import com.employee.api.v1.model.dto.UserDto;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserCsvUtil {

    public static List<UserDto> importCsv(InputStream inputStream) throws IOException {
        List<UserDto> users = new ArrayList<>();
        BeanWrapperFieldSetMapper<UserDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(UserDto.class);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
                tokenizer.setNames("username", "password");
                FieldSet fieldSet = tokenizer.tokenize(line);

                // Skip empty lines
                if (fieldSet.getFieldCount() > 0) {
                    UserDto user = null;
                    try {
                        user = fieldSetMapper.mapFieldSet(fieldSet);
                    } catch (BindException e) {
                        throw new RuntimeException(e);
                    }
                    users.add(user);
                }
            }
        }
        return users;
    }


    public static void export(PrintWriter writer, List<UserDto> users) {
        writer.println("Username,Password");
        for (UserDto user : users) {
            writer.println(user.getUsername() + "," + user.getPassword());
        }
    }
}
