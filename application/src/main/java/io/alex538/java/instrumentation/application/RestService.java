package io.alex538.java.instrumentation.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @Slf4j
public class RestService {

    public String fetchData() {
        //  of course :) emulating call to a sloooow remote system...
        ThreadUtils.sleep();

        return "Dummy data with some id " + UUID.randomUUID().toString();
    }

}
