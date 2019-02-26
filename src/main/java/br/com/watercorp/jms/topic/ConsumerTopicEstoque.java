package br.com.watercorp.jms.topic;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class ConsumerTopicEstoque {

    private static final String TOPIC_PROMOCOES = "topic.promocoes";
    private static final String CLIENT_ID = "waterkemper-1";

    public static void main(String[] args) throws JMSException, NamingException {
        InitialContext initialContext = new InitialContext();

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

        Connection connection = connectionFactory.createConnection();
        connection.setClientID(CLIENT_ID);

        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topico = session.createTopic(TOPIC_PROMOCOES);

        MessageConsumer consumer = session.createConsumer(topico);


        consumer.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {

                TextMessage textMessage = (TextMessage) message;

                try {
                    System.out.println(textMessage.getText());
                    throw new RuntimeException("Erro causado propostitalmente para validar o envio para a DLQ");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        });

        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
    }

}
