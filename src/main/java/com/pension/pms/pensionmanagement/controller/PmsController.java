package com.pension.pms.pensionmanagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pension.pms.pensionmanagement.dto.LoginDto;
import com.pension.pms.pensionmanagement.dto.PensionerDetails;
import com.pension.pms.pensionmanagement.dto.ProcessPension;
import com.pension.pms.pensionmanagement.dto.UserDto;
import com.pension.pms.pensionmanagement.model.UserPensioner;
import com.pension.pms.pensionmanagement.proxy.PensionerDetailsProxy;
import com.pension.pms.pensionmanagement.proxy.ProcessPensionProxy;
import com.pension.pms.pensionmanagement.repository.UserPensionerRepository;

@RestController
// @CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin("*")
@RequestMapping("/pension")
public class PmsController {
    // @Autowired
    // private PensionerRepository pensionerRepository;
    @Autowired
    private PensionerDetailsProxy pdProxy;
    @Autowired
    private ProcessPensionProxy ppProxy;

    @Autowired
    private UserPensionerRepository userPensionerRepository;
    
    @GetMapping("/complete-details/{id}")
    public ProcessPension showCompleteDetails(@PathVariable(name = "id") String id)
    {
        ProcessPension requestDto = this.ppProxy.processPension(id);
        return requestDto;
    }

    @GetMapping("/profile-details/{id}")
    public PensionerDetails showProfileDetails(@PathVariable(name = "id") String id)
    {
        PensionerDetails requestDto = this.pdProxy.pensionerDetailsByAadhaar(id);
        return requestDto;
    }

    @PostMapping("/post")
    public ResponseEntity<?> createNewPensioner(@RequestBody UserDto uDto){
        // add check for username exists in a DB
        if(userPensionerRepository.existsByUsername(uDto.getUsername()))
        {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        userPensionerRepository.save(new UserPensioner(uDto.getUsername(), uDto.getPassword(), uDto.getAadhaarNumber()));
        // userPensionerRepository.save(new UserPensioner(uDto.getUsername(), uDto.getPassword()));
        PensionerDetails pDto = new PensionerDetails(uDto.getAadhaarNumber(), uDto.getName(), uDto.getDob(), uDto.getPan(), uDto.getSalaryEarned(), uDto.getAllowances(), uDto.getPensionType(), uDto.getBankType());
        ResponseEntity<PensionerDetails> postProfileDetails = this.pdProxy.createNewPensioner(pDto);

        return postProfileDetails;
    }

    @PostMapping("/login")
    public ResponseEntity<?> validatePensioner(@RequestBody LoginDto uDto)
    {
        if(! userPensionerRepository.existsByUsername(uDto.getUsername()))
            return new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);
        Optional<UserPensioner> user = userPensionerRepository.findByUsername(uDto.getUsername());

        //aadhaarNumber is retrieved from login form
        // if(user != null && user.get().getPassword().equals(uDto.getPassword()) && user.get().getAadhaarNumber().equalsIgnoreCase(uDto.getAadhaarNumber()))
        // {
        //     return new ResponseEntity<>("Login Successful", HttpStatus.ACCEPTED);
        // }

        // If aadhaarNumber is not be retrieved from login form
        if(user != null && user.get().getPassword().equals(uDto.getPassword()))
        {
            return new ResponseEntity<>(user.get().getAadhaarNumber(), HttpStatus.ACCEPTED);
        }
        
        return new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);
    }
}
