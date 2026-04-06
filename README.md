# Alcohol Consumption Service

Учебный проект на Spring Boot по обработке данных о потреблении алкоголя в России (2017–2023) на основе CSV-датасета.

Проект реализует несколько вариантов работы с данными (CSV, JDBC, JPA), авторизацию, а также различные типы тестирования.

---

## Используемый стек

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- H2 Database
- Maven
- Mockito, JUnit
- CSV dataset (Kaggle)

---

## Данные

Используется датасет:

Consumption of alcoholic beverages 2017–2023 (Pivot table)

---

## Реализованные лабораторные работы

### Л2 - Работа с CSV

- Реализован сервис для чтения CSV файла
- Данные загружаются в память
- Реализован CRUD через контроллер
- Без использования БД

Особенности:
- Простая реализация
- Быстрая загрузка данных


---

### Л3 - JDBC

- Подключение к базе данных (H2)
- Создание таблицы
- Реализация SQL-запросов вручную
- Репозиторий для работы с БД


---

### Л4 - JPA

- Переход с JDBC на JPA
- Использование Entity
- Автоматическая генерация SQL


---

### Л5 - Spring Profiles

- Добавлено переключение между:
    - CSV
    - JDBC
    - JPA
- Использование `@Profile`

---

### Л6 - Spring Security (в коде)

- Реализована базовая авторизация
- Пользователи задаются в коде
- Используется HTTP Basic

Доступ:
- user / user123
- admin / admin123

---

### Л7 - Пользователи в БД

- Пользователи перенесены в базу данных
- Реализован UserDetailsService
- Добавлена сущность AppUser

---

### Л8 - TestConfiguration

- Тестирование сервиса без БД
- Использование in-memory репозитория
- Изоляция бизнес-логики

---

### Л9 - Mockito

- Тестирование сервиса без Spring
- Мокирование репозитория
- Проверка вызовов методов

---

### Л10 - WebMvcTest

- Тестирование контроллера
- MockMvc для HTTP-запросов
- Проверка JSON и статусов

---

## Как запустить проект

### 1. Клонирование

```bash
git clone <ссылка_на_репозиторий>
cd alcohol-consumption-service

### 2. Сборка проекта

mvn clean install
```

---

### 3. Запуск приложения

По умолчанию:

```bash
mvn spring-boot:run
```

Или через jar:

```bash
java -jar target/alcohol-consumption-service-0.0.1-SNAPSHOT.jar
```

---

### 4. Профили

Можно запускать с разными профилями:

- CSV:
```bash
spring.profiles.active=csv
```

- JDBC:
```bash
spring.profiles.active=jdbc
```

- JPA:
```bash
spring.profiles.active=jpa
```

Пример:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=jpa
```

---

### 5. API

Базовый URL:

```
http://localhost:8080/api/alcohol-consumption
```

Примеры:

Получить запись:

```bash
curl http://localhost:8080/api/alcohol-consumption/1
```

Создать запись:

```bash
curl -X POST http://localhost:8080/api/alcohol-consumption \
  -H "Content-Type: application/json" \
  -d '{
    "region": "Test",
    "year": 2024,
    "beverageType": "Beer",
    "consumptionThousandsDecaliters": 10.0,
    "consumptionLitersPerCapita": 1.0,
    "consumptionPureAlcoholPerCapita": 0.2
  }'
```

Удалить запись:

```bash
curl -X DELETE http://localhost:8080/api/alcohol-consumption/1
```

---

### 6. Авторизация

Используется HTTP Basic.

Пользователи:

- user / user123
- admin / admin123

Пример запроса:

```bash
curl -u user:user123 http://localhost:8080/api/alcohol-consumption/1
```

---

### 7. H2 Console

Доступна по адресу:

```
http://localhost:8080/h2-console
```

JDBC URL:

```
jdbc:h2:mem:alcoholdb
```

---

### 8. Тесты

Запуск:

```bash
mvn test
```

Типы тестов:

- Unit (Mockito)
- Service tests (TestConfiguration)
- Controller tests (WebMvcTest)
