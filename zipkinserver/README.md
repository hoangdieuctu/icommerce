# Zipkin Server

Zipkin is an open source project that provides mechanisms for sending, receiving, storing, and visualizing traces.<br> 
This allows us to correlate activity between servers and get a much clearer picture of exactly what is happening in the services.

**Version**
```
2.12.9
```

**Run**<br>

```bash
java -jar zipkinserver.jar
```

**Client config**<br>
*By default, the client will use http://localhost:9411*
```yaml
spring:
  zipkin:
    baseUrl: http://localhost:9411
```

**Access**<br>
```
http://localhost:9411
```