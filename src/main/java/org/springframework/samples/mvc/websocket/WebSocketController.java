package org.springframework.samples.mvc.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

@Controller
@MessageMapping("/calcApp")
public class WebSocketController {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    @Inject
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    @MessageMapping("/calculate")
    public void calculate(CalcInput input) throws Exception {
        Thread.sleep(2000);
        messagingTemplate.convertAndSend("/topic/showResult",
            new Result(input.getNum1() + "+" + input.getNum2() + "=" + (input.getNum1() + input.getNum2())));
        Thread.sleep(1000);
        messagingTemplate.convertAndSend("/topic/showResult",
            new Result(input.getNum1() + "-" + input.getNum2() + "=" + (input.getNum1() - input.getNum2())));
        Thread.sleep(1000);
        messagingTemplate.convertAndSend("/topic/showResult",
            new Result(input.getNum1() + "*" + input.getNum2() + "=" + (input.getNum1() * input.getNum2())));
        Thread.sleep(1000);
        messagingTemplate.convertAndSend("/topic/showResult",
            new Result("Done!"));
    }

}