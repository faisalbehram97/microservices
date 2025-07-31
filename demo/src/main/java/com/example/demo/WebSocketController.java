package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    /**
     * Handles messages sent to "/hello".
     * The message body will be converted to a Message object.
     * The return value is broadcast to all subscribers of "/topic/greetings".
     *
     * @param message The incoming message from the client.
     * @return A new Message object containing the sanitized content.
     */
    @MessageMapping("/hello") // Maps messages from clients with destination "/app/hello"
    @SendTo("/topic/greetings") // Sends the return value to subscribers of "/topic/greetings"
    public Message greeting(Message message) throws InterruptedException {
        // Simulate a delay for demonstration purposes
        Thread.sleep(1000); // simulated delay
        // Sanitize the input message content to prevent XSS attacks
        return new Message("Hello, " + HtmlUtils.htmlEscape(message.getContent()) + "!");
    }
}