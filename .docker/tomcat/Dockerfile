FROM tomcat:9

WORKDIR /usr/local/app/

RUN apt-get update \
    && apt-get install -y mc \
    && apt-get install maven \
    && apt-get install git \
    && apt-get clen

RUN git clone https://github.com/sonik9/Blog.git
RUN cd Blog/
RUN mvn clean install

