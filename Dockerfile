FROM quay.io/wildfly/wildfly

WORKDIR /

ENV WILDFLY_HOME=/opt/jboss/wildfly
ENV WILDFLY_USER=admin
ENV WILDFLY_PASSWORD=password
ENV DATA_SOURCE_CLI=add-datasource.cli

USER root
RUN mkdir /resources
USER jboss

COPY ./postgresql.jar /resources
COPY ./$DATA_SOURCE_CLI /resources
COPY ./target/SBSAAssessment-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments

RUN ls /resources

RUN /opt/jboss/wildfly/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASSWORD --silent
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/resources/$DATA_SOURCE_CLI

# Fix for Error: Could not rename /opt/jboss/wildfly/standalone/configuration/standalone_xml_history/current
RUN rm -rf $WILDFLY_HOME/standalone/configuration/standalone_xml_history

EXPOSE 8080 9990

CMD ["/bin/bash", "-c", "sleep 60 & $WILDFLY_HOME/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 "]
