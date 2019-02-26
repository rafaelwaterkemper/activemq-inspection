package br.com.watercorp.jms.queue;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

/**
 * As mensagens enviadas a topicos com consumidores {durableSuscriber()} são enviadas a DLQ, caso não haja um subscriber,
 * as mesmas são perdidas
 */

public class ConsumidorDLQ {

    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext = new InitialContext();

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        Destination destination = session.createQueue("ActiveMQ.DLQ");

        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    //Utilizado quando a sessão é configurado como CLIENT_ANKNOWLEDGE
                    //a mensagem sempre vai ser consumida quando iniciar o consumidor
                    //nunca mudando de status difertente de mensagens pendentes
//                    message.acknowledge();

                    //Utiliados com a opção SESSION_TRANSACTED
                    //session.acommit();
                    //session.rollback();
                    System.out.println(((TextMessage) message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });


        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        initialContext.close();
    }
}