# Product Management Apps

This workspace contains two implementations of the same basic product management features.

## Mono (monolith)

Structure:

- mono/client (React)
- mono/server (NodeJS + Express + MongoDB)

Run:

1. Start MongoDB locally.
2. In mono/server:
   - Copy .env.example to .env and adjust if needed.
   - Install and run:
     - npm install
     - npm run dev
3. In mono/client:
   - npm install
   - npm run dev

The UI runs on http://localhost:3000 and proxies API calls to http://localhost:3001.

## Service-based

Structure:

- service-based/client (React)
- service-based/gateway (API Gateway)
- service-based/product-service (Product CRUD)
- service-based/inventory-service (Stock by productId)

Run:

1. Start MongoDB locally.
2. In service-based/product-service:
   - Copy .env.example to .env and adjust if needed.
   - npm install
   - npm run dev
3. In service-based/inventory-service:
   - Copy .env.example to .env and adjust if needed.
   - npm install
   - npm run dev
4. In service-based/gateway:
   - Copy .env.example to .env and adjust if needed.
   - npm install
   - npm run dev
5. In service-based/client:
   - npm install
   - npm run dev

The UI runs on http://localhost:3000 and proxies to the gateway on http://localhost:3001.
