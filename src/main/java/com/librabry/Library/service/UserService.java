package com.librabry.Library.service;

import com.librabry.Library.dto.UserRequest;
import com.librabry.Library.model.*;
import com.librabry.Library.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;
    private static final Log logger = LogFactory.getLog(UserService.class);

    public User addStudent(UserRequest userRequest) {
        User user = userRequest.toUser();
        user.setUserType(UserType.STUDENT);
        return userRepository.save(user);
    }

    public User addAdmin(UserRequest userRequest) {
        User user = userRequest.toUser();
        user.setUserType(UserType.ADMIN);
        return userRepository.save(user);

    }


    public List<User> filterUser(String filterType, String operator, String value) {

        String[] filters = filterType.split(",");
        String[] operators = operator.split(",");
        String[] values = value.split(",");
        StringBuilder query = new StringBuilder();
        //query.append("");
        for (int i = 0; i < operators.length; i++) {
            UserFilterType userFilterType = UserFilterType.valueOf(filters[i]);
            Operator operator1 = Operator.valueOf(operators[i]);
            String value1 = values[i];
            query.append(userFilterType).append(operator1.getValue()).append("'").
                    append(value1).append("'").append(" and ");
            //logger.info("Query Message"+query);

//            switch (userFilterType){
//                case NAME :  // Checks only one filterType
//                    return
//            }

        }
        System.out.println(query);
        String finalQuery = "SELECT * FROM user WHERE " + query.substring(0, query.length() - 4);
        Query query1 = em.createNativeQuery(finalQuery, User.class);

        return query1.getResultList();
        //return userRepository.findUserByNativeQuery(query.substring(0, query.length() - 5).toString());
    }


    public User getStudentByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumberAndUserType(phoneNumber, UserType.STUDENT);
    }
}
