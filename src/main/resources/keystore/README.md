#To Create New Keystore
1. keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -keystore financialhouse-keystore.jks -validity 3650
2. keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore financialhouse-keystore.p12 -validity 3650
3. keytool -list -v -keystore financialhouse-keystore.jks
4. keytool -list -v -storetype pkcs12 -keystore financialhouse-keystore.p12
5. keytool -importkeystore -srckeystore financialhouse-keystore.jks -destkeystore financialhouse-keystore.p12 -deststoretype pkcs12