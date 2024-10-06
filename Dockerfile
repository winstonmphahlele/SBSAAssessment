# create java 11 wildfly container
FROM openjdk:11-jdk-slim as java11-wildfly33

ENV WILDFLY_VERSION=33.0.2.Final
ENV WILDFLY_HOME=/opt/jboss/wildfly

# Create jboss user and group
RUN addgroup --system jboss && adduser --system --ingroup jboss jboss

#intall updates and curl
RUN apt-get update && \
    apt-get install -y curl tar && \
    rm -rf /var/lib/apt/lists/*

#download wildfly
RUN curl -L -o /tmp/wildfly.tar.gz https://github.com/wildfly/wildfly/releases/download/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz && \
    mkdir -p /opt/jboss && \
    tar -xzf /tmp/wildfly.tar.gz -C /opt/jboss && \
    mv /opt/jboss/wildfly-$WILDFLY_VERSION $WILDFLY_HOME && \
    rm /tmp/wildfly.tar.gz

# Change ownership of WildFly directory
RUN chown -R jboss:jboss $WILDFLY_HOME

#build to container deployment container from previous build
FROM java11-wildfly33

ENV WILDFLY_USER=admin
ENV WILDFLY_PASSWORD=password
ENV DATA_SOURCE_CLI=add-datasource.cli

WORKDIR /

RUN mkdir /resources

USER jboss

#copy driver and war. create DS
COPY ./postgresql.jar /resources
COPY ./$DATA_SOURCE_CLI /resources
COPY ./target/SBSAAssessment-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments

RUN ls /resources

RUN $WILDFLY_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASSWORD --silent
RUN $WILDFLY_HOME/bin/jboss-cli.sh --file=/resources/$DATA_SOURCE_CLI

RUN rm -rf $WILDFLY_HOME/standalone/configuration/standalone_xml_history

EXPOSE 8080 9990

CMD ["/bin/bash", "-c", "$WILDFLY_HOME/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0"]
