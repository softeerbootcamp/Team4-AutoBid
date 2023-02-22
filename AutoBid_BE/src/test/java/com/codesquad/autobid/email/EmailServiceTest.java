package com.codesquad.autobid.email;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaUserDTO;
import com.codesquad.autobid.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codesquad.autobid.util.AuctionTestUtil.saveAuction;
import static com.codesquad.autobid.util.CarTestUtil.getNewCars;
import static com.codesquad.autobid.util.CarTestUtil.saveCar;
import static com.codesquad.autobid.util.UserTestUtil.getNewUser;
import static com.codesquad.autobid.util.UserTestUtil.saveUser;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    public EmailService emailService;

    @Test
    @DisplayName("이메일 보내기 성공")
    void sendEmail() {
        // given
        User user = saveUser(getNewUser());
        user.setEmail("wonjulee.dev@gmail.com");
        Car car = saveCar(getNewCars(user.getId(), 1).get(0));
        Auction auction = saveAuction(user, car);
        AuctionKafkaUserDTO auctionKafkaUserDTO = AuctionKafkaUserDTO.of(user.getId(), user.getName(), user.getMobilenum(), 10000l, user.getEmail());
        // when
        emailService.send(auction.getAuctionTitle(), auction.getAuctionEndPrice(), auctionKafkaUserDTO);
        // then
    }
}
