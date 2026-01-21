package com.HimanshuBagga.TestingSpringBoot.Service.impl;

import com.HimanshuBagga.TestingSpringBoot.Service.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DataServiceImplDev implements DataService {
    @Override
    public String getData() {
        return "Dev Data";
    }
}
