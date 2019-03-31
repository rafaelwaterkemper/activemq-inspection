package br.com.watercorp.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer {

    private static final String QUEUE_NOVAFILA = "queue.novafila";

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Connection connectionPerformance = connectionFactory.createConnection();
        connectionPerformance.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Session sessionTwo = connectionPerformance.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(QUEUE_NOVAFILA);
        Destination destinationPerformance = sessionTwo.createQueue("queue.testeperformance");

        MessageConsumer consumer = session.createConsumer(destination);
        MessageConsumer consumerPerformance = sessionTwo.createConsumer(destinationPerformance);

        long startTime = new Date().getTime();

        System.out.println("inicio performance " + LocalDateTime.now());

        AtomicInteger sequencialPerformanceQueue = new AtomicInteger(1);
        consumerPerformance.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    int current = sequencialPerformanceQueue.getAndIncrement();
                    if (current == 50000) {
                        System.out.println("End performance " + LocalDateTime.now());
                        TimeUtils.getTimeBetweenTimesInSeconds(startTime, new Date().getTime());
                    }
//                    throw new RuntimeException("Erro causado propostitalmente para validar o envio para a DLQ");
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });

        AtomicInteger sequencialNormalQueue = new AtomicInteger(1);
        System.out.println("inicio normal queue " + LocalDateTime.now());
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    int current = sequencialNormalQueue.getAndIncrement();
                    if (current == 50000) {
                        System.out.println("End " + LocalDateTime.now());
                        TimeUtils.getTimeBetweenTimesInSeconds(startTime, new Date().getTime());
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });

       new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        initialContext.close();
    }
}
