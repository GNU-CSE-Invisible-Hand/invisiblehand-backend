package com.rrkim.core.common.controller;

import com.rrkim.core.common.dto.ApiResponse;
import com.rrkim.core.common.util.HashMapCreator;
import com.rrkim.core.common.util.ApiUtility;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Operation(hidden = true)
    @GetMapping("/")
    public @ResponseBody ApiResponse index() {
        return ApiUtility.createResponse(true, HashMapCreator.getStringObjectBuilder().put("data", "Hello World!").build());
    }
}
