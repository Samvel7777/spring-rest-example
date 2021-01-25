package am.itspace.springrestexample.service;

public interface SmsService {

    String send(String phoneNumber, String message);
}
