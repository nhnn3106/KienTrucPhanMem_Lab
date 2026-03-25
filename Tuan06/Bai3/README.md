# Bai 3 - Demo DB Sharding, Vertical, Functional

## Muc tieu

- DB: 2 table `table_user_01` va `table_user_02` theo dieu kien gioi tinh (nam -> 01, nu -> 02).
- Vertical: mo phong theo feature (user, auth).
- Functional: mo phong theo tang chuc nang (domain, application, infrastructure, interface).
- Tang performance: goi y index, read-only transaction, tach bang.

## Yeu cau

- JDK 17+
- PostgreSQL

## Cau hinh DB

Cap nhat thong tin DB trong file [src/main/resources/application.yml](src/main/resources/application.yml).

## Chay ung dung

```bash
mvn spring-boot:run
```

## API mau

### Sharding

- POST /api/sharded/users

```json
{ "name": "An", "email": "an@example.com", "gender": "MALE" }
```

- GET /api/sharded/users/MALE/1
- GET /api/sharded/users

### Vertical

- POST /api/vertical/users
- GET /api/vertical/users/1
- GET /api/vertical/users
- POST /api/vertical/auth/login

### Functional

- POST /api/functional/users
- GET /api/functional/users/1
- GET /api/functional/users

## Goi y tang performance

- Tach bang theo dieu kien de giam kich thuoc moi bang.
- Index tren cot `email` de tra cuu nhanh.
- Su dung `@Transactional(readOnly = true)` cho query chi doc.
- Neu luong du lieu lon, co the them paging hoac cache.
