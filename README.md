# General
Market Data Service is a messaging base pricing application. It has two message flows within the design and implementation
1. Inbound message flow:
2. Outbound message flow:
All inbound and outbound messages payload format is defined as binary formats 

## Inbound flow
External feeds supply raw market data feed through a UDP protocol. Market Data feed contains a full snapshot or 
delta update. All inbound message are placed at an internal queue prior the downstream processing

## Pricing
Pricing function is implemented inside MicroPriceCalculator class at a separated project. Each inbound message 
will trigger a pricing calibration and an outbound message   

## Outbound flow
Outbound message will send to remote TCP application that are consuming the calculated price,  




# Build the project
It is a maven project, so you can run one of the following command to compile, and/or test, 
packet jar to local .m2 repo 

mvn clean install

mvn clean install -Dmaven.test.skip=true
