package com.bbl.test.repositories;

import com.bbl.test.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class UserRepository {

    private Random random = new Random();
    private List<User> static_users = new ArrayList<>(List.of(
            new User(1L, "Leanne Graham", "Bret", "Sincere@april.biz", "1-770-736-8031 x56442", "hildegard.org"),
            new User(2L, "Ervin Howell", "Antonette", "Shanna@melissa.tv", "010-692-6593 x09125", "anastasia.net"))
            //                new User(3L, "Leanne Graham", "Bret", "Sincere@april.biz", "1-770-736-8031 x56442", "hildegard.org"),
    );

    public List<User> getAllUsers() {
        return static_users;
    }

    public User findUserById(Long id) {
        return static_users.stream().filter(user -> user.getId().equals(id)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID: " + id + " not Found"));
    }

    public void createUser(String name, String username, String email, String phone, String website){
        Long generatedId = Math.abs(random.nextLong());
        User newUser = new User(generatedId, name, username, email, phone, website);
        static_users.add(newUser);
    }

    public void updateUser(Long id, String name, String username, String email, String phone, String website) {
        boolean found = false;
        for(User user : static_users) {
            if(user.getId().equals(id)) {
                user.setName(name);
                user.setUsername(username);
                user.setEmail(email);
                user.setPhone(phone);
                user.setWebsite(website);
                found = true;
                break;
            }
        }

        if(!found) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID: " + id + " not Found");
        }
    }

    public void deleteUser(Long id) {
        int index = -1;

        for(int i = 0 ; i < static_users.size(); i++) {
            User user = static_users.get(i);
            if(user.getId().equals(id)) {
                index = i;
                break;
            }
        }

        if(index == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID: " + id + " not Found");
        } else {
            static_users.remove(index);
        }
    }
}
