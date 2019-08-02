Acquired Knowledge
--

* `VirtualTopics will be save how persistent messages, indeppendent of delivery mode,
since the consumer was already created`

* `Topics, will use percent stored used if delivery mode is PERSISTENT and exist a durable
subscriber to a topic`

* `Queues, will use percent stored used if delivery mode is PERSISTENT`

Create activemq container with disk space with a defined size, for help with tests.
- docker run --name activeconfig5mb -d -p 8161:8161 -p 61616:61616 -e 'ACTIVEMQ_CONFIG_STOREUSAGE=5mb' webcenter/activemq
