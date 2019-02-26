package br.com.watercorp.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class Producer {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination queue = session.createQueue("queue.novafila");
        Destination topic = session.createTopic("topic.promocoes");

        MessageProducer producerQueue = session.createProducer(queue);
        MessageProducer producerTopic = session.createProducer(topic);

        for (int i = 0; i < 2; i++) {
            producerTopic.send(
                    session.createTextMessage("Testando produção de mensagem a um tópico - " + i));
        }

        for (int i = 0; i < 2; i++) {
            producerQueue.send(
                    session.createTextMessage("Testando produção de mensagem a um tópico - " + i));
        }

        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        initialContext.close();
    }
}
