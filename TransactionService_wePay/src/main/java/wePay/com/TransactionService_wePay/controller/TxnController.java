package wePay.com.TransactionService_wePay.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wePay.com.TransactionService_wePay.service.TxnService;

@RestController
@RequestMapping("/txn")
public class TxnController {

    @Autowired
    private TxnService txnService;

    @PostMapping("/initTxn")
    public String initTransaction(@RequestParam("receiver") String receiver,
                                  @RequestParam("purpose") String purpose,
                                  @RequestParam("amount") String amount) throws JsonProcessingException {

        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return txnService.initTxn(userDetails.getUsername(), receiver, amount, purpose);
    }
}