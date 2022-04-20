package com.cloudalibaba.seata.account.controller;

import com.cloudalibaba.seata.account.service.AccountService;
import com.cloudalibaba.seata.account.service.AccountTccService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author YUDI-Corgi
 * @description AccountController
 */
@RestController
@RequestMapping("account")
public class AccountController {

    @Resource
    private AccountService accountService;
    @Resource
    private AccountTccService tccService;

    @PutMapping("/{userId}/{money}")
    public ResponseEntity<Void> deduct(@PathVariable("userId") String userId, @PathVariable("money") Integer money){
        accountService.deduct(userId, money);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("tcc/{userId}/{money}")
    public ResponseEntity<Void> tccDeduct(@PathVariable("userId") String userId, @PathVariable("money") Integer money){
        tccService.deduct(userId, money);
        return ResponseEntity.noContent().build();
    }
}
