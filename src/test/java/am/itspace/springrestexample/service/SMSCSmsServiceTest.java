package am.itspace.springrestexample.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SMSCSmsServiceTest {

//    @Autowired
//    @Qualifier("SMSCSmsService")
//    private SmsService smsService;

    private SmsService smsService = Mockito.mock(SMSCSmsService.class);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void send() {
        Mockito.when(smsService.send("+37493314310", "Test")).thenReturn("123456");

        String test = smsService.send("+37493314310", "Test");
        assertNotNull(test);
    }
}