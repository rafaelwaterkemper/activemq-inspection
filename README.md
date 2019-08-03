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