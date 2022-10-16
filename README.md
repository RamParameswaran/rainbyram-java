# rainbyram-java
Serverless application to send SMS alerts when it is forecast to rain

### Configuration
- The app currently supports only one location at a time, specified in `app/src/main/resources/config.properties`
- Additionally, the following environment variables are required at runtime:
    - `OWM_API_KEY` - api key from [OpenWeathermap](https://openweathermap.org/api)
    - `SNS_TOPIC_ARN` - AWS SNS Topic ARN. SNS subscriptions are used to send email/SMS to subscribers. The SNS Topic and Subscriptions must be configured separately in AWS.

### Test & Build
- Run unit tests: `./gradlew test`
- Build to zip: `./gradlew buildZip`
