package com.librabry.Library.service;

import com.librabry.Library.dto.UserRequest;
import com.librabry.Library.exception.TxnException;
import com.librabry.Library.model.*;
import com.librabry.Library.repository.UserCacheRepository;
import com.librabry.Library.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;
    @Value("${student.authority}")
    private String studentAuthority;
    @Value("${admin.authority}")
    private String adminAuthority;
    private static final Log logger = LogFactory.getLog(UserService.class);
    @Autowired
    private PasswordEncoder encoder;

    public User addStudent(UserRequest userRequest) {
        User user = userRequest.toUser();
        user.setUserType(UserType.STUDENT);
        user.setAuthorities(studentAuthority);
       // user.setPassword("password");
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User addAdmin(UserRequest userRequest) {
        User user = userRequest.toUser();
        user.setUserType(UserType.STUDENT);
        user.setAuthorities(adminAuthority);
        user.setPassword(encoder.encode(user.getPassword()));
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
    @Autowired
    private UserCacheRepository userCacheRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //load the user from redis first
        //if the ser present in redis,I want to get data from redis


        User user=userCacheRepository.getUser(email);
        if(user!=null)
        {
            return user;
        }

        user= userRepository.findByEmail(email);
        if(user==null)
        {
            new TxnException("User Exception");
        }
        userCacheRepository.setUser(email,user);

        return user;
        //if the data is not present in redis I want to go to DB and check it in DB
        //if data present in DB ,then I will keep that data in my cache as well;
       // return null;
    }
}
