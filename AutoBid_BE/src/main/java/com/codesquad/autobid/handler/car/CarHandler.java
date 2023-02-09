package com.codesquad.autobid.handler.car;

import com.codesquad.autobid.handler.car.vo.CarListVO;
import com.codesquad.autobid.handler.car.vo.CarVO;
import com.codesquad.autobid.handler.car.vo.DistanceVO;
import com.codesquad.autobid.test.CarTestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CarHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestTemplate rt = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(CarHandler.class);

    @Value("${hyundai.car.car_list_request_uri}")
    private String CAR_LIST_REQUEST_URL;
    @Value("${hyundai.car.car_detail_request_uri}")
    private String CAR_DETAIL_REQUEST_URL;

    public List<CarVO> getCars(String accessToken) {
        // HttpEntity request = getAuthorizedRequest(accessToken);
        // ResponseEntity<String> carListResponse = rt.exchange(
        //         CAR_LIST_REQUEST_URL,
        //         HttpMethod.GET,
        //         request,
        //         String.class
        // );
        // return parseToCars(carListResponse.getBody()); // True Code
        return parseToCars(CarTestUtil.getCarListJson()).getCars(); // Test Code
    }

    private CarListVO parseToCars(String body) {
        try {
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            System.out.println("hi");
            e.printStackTrace();
            logger.debug("unhandled car list request exception");
            return new CarListVO();
        }
    }

    public DistanceVO getDistance(String accessToken, String carId) {
        // HttpEntity request = getAuthorizedRequest(accessToken);
        // ResponseEntity<String> carListResponse = rt.exchange(
        //         String.format(CAR_DETAIL_REQUEST_URL, carId),
        //         HttpMethod.GET,
        //         request,
        //         String.class
        // );
        // return parseToAvailableDistance(carListResponse.getBody()); // True Code
        return parseToAvailableDistance(CarTestUtil.getDistance()); // Test Code
    }

    private DistanceVO parseToAvailableDistance(String body) {
        try {
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.debug("unhandled car list request exception");
            return new DistanceVO();
        }
    }

    private HttpEntity getAuthorizedRequest(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<Void>(header);
    }
}
