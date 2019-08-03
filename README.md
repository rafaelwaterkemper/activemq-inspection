##Acquired Knowledge
--

* `VirtualTopics will be save how persistent messages if delivery mode is PERSISTENT,
and has a Consumer.A.VirtualTopic created`

* `Topics, will use percent stored used if delivery mode is PERSISTENT and exist a durable
subscriber to a topic`

* `Queues, will use percent stored used if delivery mode is PERSISTENT`

Queueu = Persistent = STORE percent used
Queueu = Non Persisten = TEMP percent used

Topic = non durable subscriber = dont save message
Topic = durable subscriber = Non Persistent = TEMP percent used
Topic = durable subscriber = Persistent = STORE percent used

VirtualTopic = without a consumer = dont save message
VirtualTopic = consumer already existent = Non Persistent = TEMP percent used
VirtualTopic = consumer already existent = Persistent = STORE percent used

Create activemq container with disk space with a defined size, for help with tests.
- docker run --name activeconfig5mb -d -p 8161:8161 -p 61616:61616 -e 'ACTIVEMQ_CONFIG_STOREUSAGE=5mb' webcenter/activemq
