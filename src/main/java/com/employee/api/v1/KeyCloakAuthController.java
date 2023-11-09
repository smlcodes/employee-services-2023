package com.employee.api.v1;

import com.employee.ApplicationConstants;
import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.api.v1.model.dto.EmployeeSearchDto;
import com.employee.api.v1.model.dto.EmployeeSearchResultsDto;
import com.employee.api.v1.model.dto.RequestDto;
import com.employee.api.v1.model.mapper.EmployeeMapper;
import com.employee.exception.BusinessException;
import com.employee.service.EmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Satya Kaveti
 */

@RestController
@RequestMapping(path = ApplicationConstants.API_BASE + ApplicationConstants.V1 + "keycloak/auth/")
@Validated
@Slf4j
@RequiredArgsConstructor
public class KeyCloakAuthController {



}