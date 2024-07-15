package com.example.basic;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {
    @Override
    public void save(User user) {
        HashMap<String, String> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        String[] aa = (String[])list.toArray();
        System.out.println("UserRepository.save");
    }
}
