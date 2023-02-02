package com.codesquad.autobid.handler.car;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.handler.car.enums.CarFindErrorType;
import com.codesquad.autobid.handler.car.vo.CarFindErrorResponseVO;
import com.codesquad.autobid.handler.car.vo.CarVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarHandler<T> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestTemplate rt = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(CarHandler.class);

    @Value("${hyundai.car.request_uri}")
    private String REQUEST_URL;

    public List<Car> getCars(String accessToken) {
        HttpEntity request = getAuthorizedRequest(accessToken);
        ResponseEntity<String> response = rt.exchange(
                REQUEST_URL,
                HttpMethod.GET,
                request,
                String.class
        );
        return parse(response.getBody());
    }

    private List<Car> parse(String body) {
        if (body.contains("errId")) {
            return parseError(body);
        }
        return parseCars(body);
    }

    private List<Car> parseCars(String body) {
        try {
            return objectMapper.readValue(body, new TypeReference<List<CarVO>>() {
                    }).stream()
                    .map(Car::from)
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            logger.debug("unhandled API Exception");
        }
        return new ArrayList<>();
    }

    private List<Car> parseError(String body) {
        try {
            CarFindErrorResponseVO carFindErrorResponseVO = objectMapper.readValue(body, CarFindErrorResponseVO.class);
            CarFindErrorType error = CarFindErrorType.findByErrorCode(carFindErrorResponseVO.getErrCode());
            if (error.isCarNotExistError()) {
                return new ArrayList<>();
            }
        } catch (JsonProcessingException ee) {
            logger.debug("unhandled API Exception");
        }
        return new ArrayList<>();
    }

    private HttpEntity getAuthorizedRequest(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<Void>(header);
    }
}
