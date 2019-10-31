FROM openjdk:11-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/classes/keystore /fh-keystore
RUN cd /fh-keystore && keytool -importkeystore -srckeystore financialhouse-keystore.jks \
-destkeystore financialhouse-keystore.p12 -deststoretype pkcs12 \
-keypass financialhouse -storepass financialhouse \
-srcstorepass financialhouse -deststorepass financialhouse \
-noprompt;
COPY ${DEPENDENCY}/BOOT-INF/lib /reporting-api/lib
COPY ${DEPENDENCY}/META-INF /reporting-api/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /reporting-api
ENTRYPOINT ["java","-cp","reporting-api:reporting-api/lib/*","com.dz.financial.reporting.ReportingApplication"]