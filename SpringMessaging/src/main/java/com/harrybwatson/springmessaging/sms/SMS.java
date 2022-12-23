package com.harrybwatson.springmessaging.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SMS {

    private String to;
    private String body;


    @Override
    public String toString() {
        return String.format("SMS {to=%s, body=%s}", getTo(), getBody());
    }
}
