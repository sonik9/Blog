package pl.upir.blog.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Vitalii on 17.06.2015.
 */
public class FirstMessageListener implements MessageListener {
    private static Logger logger= LoggerFactory.getLogger(FirstMessageListener.class);
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage= (TextMessage) message;

        try {
            logger.info("Message received: "+textMessage.getText());
        }catch (JMSException e){
            logger.error(e.getMessage());
        }
    }
}
