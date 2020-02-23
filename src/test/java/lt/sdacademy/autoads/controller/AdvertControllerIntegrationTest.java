package lt.sdacademy.autoads.controller;

import lt.sdacademy.autoads.model.entity.AdvertEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdvertEntity.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdvertControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    void getAdvert() {
        AdvertEntity advert = restTemplate.getForObject(getRootUrl() + "/adverts/1", AdvertEntity.class);
        System.out.println(advert.getDateAdded());
        assertNotNull(advert);
    }

    @Test
    void create() {
        AdvertEntity advert = new AdvertEntity();
        advert.setVolume(BigDecimal.valueOf(1.7));
        advert.setTransmissionType("Manual");
        advert.setPrice(2000);
        advert.setPower(55);
        advert.setModel("Astra");
        advert.setManufactureYear((short) 2004);
        advert.setKilometers(299856);
        advert.setFuelType("Diesel");
        advert.setDriveType("FWD");
        advert.setDateAdded(LocalDateTime.now());
        advert.setColor("Silver");
        advert.setChassisType("Universal");
        ResponseEntity<AdvertEntity> postResponse = restTemplate.postForEntity(getRootUrl() + "/adverts", advert, AdvertEntity.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    void getAll() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/adverts",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }
}
