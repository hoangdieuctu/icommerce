# Authentication Service
Authenticate user and generate token for using with the Api Gateway.

## Supported Methods
List out all supported authentication methods.

### Login by Facebook
#### Url
```
/api/v1/auth/login/facebook
```
#### Method
```
GET
```
#### Response
```json
{
    "schema": "Bearer",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNTkxNDAyMjQ1LCJpc3MiOiJodHRwOi8vaWNvbW1lcmNlLmNvbSIsImV4cCI6MTU5MTQwNTg0NX0.1lav2j4QxQi3NE202gfmrF85cNGfLgUyQC5yjsmSIPQ"
}
```

## Work Flow
### Login by Facebook
![picture](login-facebook.png)

## Project Structure
```bash
|---- com.icommerce.authenticationservice
|     |-- constant
|     |-- controller
|     |-- dto
|     |-- model
|     |-- repository
|     |-- service
|     |   |-- impl
|     |-- ServerApplication
```