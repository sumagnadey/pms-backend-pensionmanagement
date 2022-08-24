package com.pension.pms.pensionmanagement.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pension.pms.pensionmanagement.dto.PensionerDetails;

@FeignClient(name = "pensioner-detail", url = "http://${EVN:localhost:8001}/pensioner-details")
public interface PensionerDetailsProxy {
    @GetMapping("/pension/PensionerDetailByAadhaar/{id}")
    public PensionerDetails pensionerDetailsByAadhaar(@PathVariable(name = "id") String id);

    @PostMapping("/pension/savepensionerdetail")
    public ResponseEntity<PensionerDetails> createNewPensioner(@RequestBody PensionerDetails pDto);

}
