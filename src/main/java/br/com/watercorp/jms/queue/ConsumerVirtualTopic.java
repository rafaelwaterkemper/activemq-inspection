package br.com.watercorp.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

public class ConsumerVirtualTopic{

    public static final String VIRTUAL_TOPIC_PROMOCOES = "Consumer.A.VirtualTopic.promocoes";

    public static void main(String[] args) throws JMSException {
        Connection connection = getConnectionLocal("tcp://localhost:61616", "admin", "admin", "waterkemper-2");

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(VIRTUAL_TOPIC_PROMOCOES);
        MessageConsumer consumer = session.createConsumer(queue);

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
