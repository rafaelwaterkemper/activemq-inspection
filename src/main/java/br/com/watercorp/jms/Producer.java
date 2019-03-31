package br.com.watercorp.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public class Producer {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination queue = session.createQueue("queue.novafila");
        Destination queuePerformance = session.createQueue("queue.testeperformance");
        Destination topic = session.createTopic("topic.promocoes");

        MessageProducer producerQueue = session.createProducer(queue);
        MessageProducer producerQueuePerformance = session.createProducer(queuePerformance);
        MessageProducer producerTopic = session.createProducer(topic);

        long starTime = new Date().getTime();

        System.out.println("inicio queu performance" + LocalDateTime.now());
        for (int i = 0; i < 50000; i++) {
            producerQueuePerformance.send(
                    session.createTextMessage("Testando produção de mensagem em performance - " + i));
        }

        System.out.println("final producer queue performance " + LocalDateTime.now());

        System.out.println("inicio queue" + LocalDateTime.now());
        for (int i = 0; i < 50000; i++) {
            producerQueue.send(
                    session.createTextMessage("Testando produção de mensagem a um tópico - " + i));
        }

        System.out.println("final producer queue " + LocalDateTime.now());
        long endTime = new Date().getTime();

        System.out.println("Tempo total de produção das mensagens " + TimeUtils.getTimeBetweenTimesInSeconds(starTime, endTime));

        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        initialContext.close();
    }

}
