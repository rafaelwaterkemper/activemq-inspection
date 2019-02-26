package br.com.watercorp.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

public class ConsumerTopicComercial {

    public static final String TOPIC_PROMOCOES = "topic.promocoes";

    public static void main(String[] args) throws JMSException {
        Connection connection = getConnectionLocal("tcp://localhost:61616", "admin", "admin", "waterkemper-2");

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topico = session.createTopic(TOPIC_PROMOCOES);

        MessageConsumer consumer = session.createDurableSubscriber(topico, "consumer-comercial");

        consumer.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {

                try {
                    System.out.println(((TextMessage) message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        });

        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
    }

    private static Connection getConnectionLocal(String host, String user, String password, String clientId) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(host);
        Connection connection = factory.createConnection(user, password);
        connection.setClientID(clientId);

        return connection;
    }

}
