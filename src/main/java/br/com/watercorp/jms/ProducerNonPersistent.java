package br.com.watercorp.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class ProducerNonPersistent {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination queue = session.createQueue("queue.novafila");

        MessageProducer producerQueue = session.createProducer(queue);

        for (int i = 0; i < 2; i++) {

            /** Mensagem nao persistente - Quando o broker for reiniciado a mensagem é perdida
             Priority - de 0 a 9, onde o 9 é o mais prioritário(Tem que alterar a propriedade prioritizedMessagges na configuracao
             do activemq.xml)
             timeToLive - tempo de vida antes de expirar a mensagem
             **/
            producerQueue.send(session.createTextMessage("Testando produção de mensagem a um tópico - " + i),
                    DeliveryMode.NON_PERSISTENT, 9, 50000);

        }

        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        initialContext.close();
    }
}
