package br.com.watercorp.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

import static java.lang.String.format;
import static java.lang.System.out;

public class Consumer {

    private static final String QUEUE_NOVAFILA = "queue.novafila";

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(QUEUE_NOVAFILA);

        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    out.println(format("Conteúdo da mensagem: %s", ((TextMessage) message).getText()));

                    throw new RuntimeException("Erro causado propostitalmente para validar o envio para a DLQ");
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
