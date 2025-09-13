# Лабораторная работа №1 по курсу "Информационная безопасность"

Стек: Spring Boot, Java, Maven

Проект: backend web-приложения, реализующее CRUD-операции с использованием Spring Security

Аутентифицированным пользователям выдаётся jwt-токен, который передаётся в cookie "token" при запросе

## API

### POST /auth/sign

Авторизация: не требуется

Логика: регистрация пользователя

Тело запроса: json

Пример использования:
```
curl -i -X POST \
   -H "Content-Type:application/json" \
   -d \
'{
    "login": "mylogin",
    "password": "mypassword"
}' \
 'http://localhost:18018/auth/sign'
```

### POST /auth/log

Авторизация: не требуется

Логика: вход пользователя в систему

Тело запроса: json

Пример использования:
```
curl -i -X POST \
   -H "Content-Type:application/json" \
   -d \
'{
    "login": "mylogin",
    "password": "mypassword"
}' \
 'http://localhost:18018/auth/log'
```

### POST /plants

Авторизация: требуется

Логика: создание растения в хранилище

Тело запроса: json

Пример использования:
```
curl -i -X POST \
   -H "Cookie:token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzU3NjE4ODQ3LCJleHAiOjE3NTc3MDUyNDcsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dfQ.7w6v9QiUXxYsXRuqgn4UQmgnEtxAscc_243s9xORm3c" \
   -H "Content-Type:application/json" \
   -d \
'{
    "name": "flower",
    "leafSize": "1"
}' \
 'http://localhost:18018/plants'
```

### GET /plants

Авторизация: требуется

Логика: получение списка растений из хранилища

Тело запроса: json

Пример использования:
```
curl -i -X GET \
   -H "Cookie:token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzU3NjE4ODQ3LCJleHAiOjE3NTc3MDUyNDcsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dfQ.7w6v9QiUXxYsXRuqgn4UQmgnEtxAscc_243s9xORm3c" \
 'http://localhost:18018/plants'
```

### PUT /plants

Авторизация: требуется

Логика: изменение данных растения из хранилища

Тело запроса: json

Пример использования:
```
curl -i -X PUT \
   -H "Cookie:token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzU3NjE4ODQ3LCJleHAiOjE3NTc3MDUyNDcsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dfQ.7w6v9QiUXxYsXRuqgn4UQmgnEtxAscc_243s9xORm3c" \
   -H "Content-Type:application/json" \
   -d \
'{
    "id": "1",
  	"name": "flower",
    "leafSize": "1"
}' \
 'http://localhost:18018/plants'
```

### DELETE /plants

Авторизация: требуется

Логика: удаление растения по id

Тело запроса: json

Пример использования:
```
curl -i -X DELETE \
   -H "Cookie:token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzU3NjE4ODQ3LCJleHAiOjE3NTc3MDUyNDcsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dfQ.7w6v9QiUXxYsXRuqgn4UQmgnEtxAscc_243s9xORm3c" \
 'http://localhost:18018/plants?id=1'
```

## Меры защиты

**Защита от SQLi** - использование JPA repository - при обращении к бд JDBC создаёт prepared statement

**Защите от XSS** - использование заголовка для бразеров:
```
xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
```
А также использование sanitizer:
```
private static final PolicyFactory POLICY = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
...
dto.setName(POLICY.sanitize(dto.getName()));
```
**Аутентификация** - использование jwt токена, написан фильтр - [SecurityJwtTokenValidator](.\src\main\java\com\example\demo\security\SecurityJwtTokenValidator.java) - для проверки токена.

Защищённость endpoint'а определяется аннотацией `@PermitAll` или `@PreAuthorize`

Пароли хранятся в зашифрованном с помощью `BCryptPasswordEncoder` виде
