package com.pension.pms.pensionmanagement.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pension.pms.pensionmanagement.dto.ProcessPension;

@FeignClient(name="process-pension", url="http://${EVN:localhost:8002}/process-pension")
public interface ProcessPensionProxy
{
    @GetMapping("/pension/show-pension-details/{id}")
    public ProcessPension processPension(@PathVariable(name = "id") String id);
}