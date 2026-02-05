# High Availability & Load Balancing với Nginx/HAProxy

## 📖 Giới thiệu

Project này demo việc triển khai **High Availability** (HA) với **Load Balancer** sử dụng Nginx hoặc HAProxy. Hệ thống tự động phát hiện khi một server chết và ngừng gửi traffic đến server đó (**Health Check** + **Auto Failover**).

### 🎯 Mục tiêu học tập

- ✅ Hiểu cách hoạt động của Load Balancer
- ✅ Cấu hình Health Check tự động
- ✅ Triển khai Automatic Failover
- ✅ So sánh Nginx vs HAProxy
- ✅ Thực hành với Docker Compose

## 🏗️ Kiến trúc

```
Client → Nginx/HAProxy → [Server 1, Server 2, Server 3] → Database
         (Port 80/8080)   (8081)    (8082)    (8083)       (3306)
```

### Cơ chế hoạt động:

1. **Load Balancer** nhận request từ client
2. Phân phối request đến các servers theo thuật toán (Round Robin, Least Connections, etc.)
3. **Health Check** liên tục kiểm tra trạng thái servers (mỗi 2-10 giây)
4. Nếu server **DOWN** → tự động loại khỏi pool
5. Nếu server **UP trở lại** → tự động thêm vào pool

## 🚀 Quick Start

### Option 1: Docker Compose (Recommended)

#### Với Nginx:
```bash
docker-compose up --build
```
Truy cập: http://localhost

#### Với HAProxy:
```bash
docker-compose -f docker-compose-haproxy.yml up --build
```
Truy cập:
- App: http://localhost:8080
- Stats: http://localhost:8404/stats (admin/admin)

### Option 2: Local Setup

Xem chi tiết trong [QUICKSTART.md](QUICKSTART.md)

## 🧪 Demo Health Check & Failover

### Test tự động phát hiện server chết:

```bash
# 1. Khởi động hệ thống (3 servers)
docker-compose up

# 2. Test load balancing
curl http://localhost/
# Mỗi lần curl sẽ nhận response từ server khác nhau

# 3. Dừng server 1
docker stop availability-server1

# 4. Tiếp tục test
curl http://localhost/
# Chỉ nhận response từ server 2 và 3
# → Nginx tự động phát hiện server 1 DOWN

# 5. Khởi động lại server 1
docker start availability-server1

# 6. Đợi 30s và test lại
curl http://localhost/
# Server 1 được đưa trở lại pool
```

### Kết quả quan sát:

- ✅ Khi 1 server chết → traffic tự động chuyển sang servers còn lại
- ✅ Hệ thống vẫn hoạt động bình thường (High Availability)
- ✅ Khi server sống lại → tự động nhận traffic trở lại
- ✅ Zero downtime từ góc độ client

## 📁 Cấu trúc Project

```
availability/
├── src/
│   └── main/
│       ├── java/fit/iuh/se/availability/
│       │   ├── AvailabilityApplication.java
│       │   └── controller/
│       │       └── HealthController.java          # REST endpoints + health check
│       └── resources/
│           ├── application.properties              # Default config
│           ├── application-server1.properties      # Server 1 (port 8081)
│           ├── application-server2.properties      # Server 2 (port 8082)
│           ├── application-server3.properties      # Server 3 (port 8083)
│           └── application-docker.properties       # Docker config
├── docker-compose.yml                              # Nginx setup
├── docker-compose-haproxy.yml                      # HAProxy setup
├── nginx.conf                                      # Nginx config (local)
├── nginx-docker.conf                               # Nginx config (Docker)
├── haproxy.cfg                                     # HAProxy config (local)
├── haproxy-docker.cfg                              # HAProxy config (Docker)
├── Dockerfile                                      # Spring Boot image
├── start-server1.bat                               # Start script server 1
├── start-server2.bat                               # Start script server 2
├── start-server3.bat                               # Start script server 3
├── test-load-balance.bat                           # Test script
├── HELP.md                                         # Hướng dẫn tổng quan
├── QUICKSTART.md                                   # Quick start guide
├── README-NGINX.md                                 # Nginx details
├── README-HAPROXY.md                               # HAProxy details
└── README.md                                       # This file
```

## 🔍 Endpoints

| Endpoint | Mô tả | Response |
|----------|-------|----------|
| `GET /` | Home page | Server info (hostname, port) |
| `GET /health` | Health check | Status, timestamp, server info |
| `GET /api/data` | Sample API | Sample data + server info |
| `GET /nginx-health` | Nginx health | Nginx status (Nginx only) |

### Ví dụ response:

```json
// GET /health
{
  "status": "UP",
  "timestamp": "2026-01-31T10:30:00",
  "port": "8081",
  "hostname": "server1",
  "ip": "172.18.0.2"
}

// GET /
{
  "message": "Welcome to Availability Service",
  "server": "Server running on port 8081",
  "hostname": "server1"
}
```

## ⚙️ Cấu hình Health Check

### Nginx (Passive Health Check)

```nginx
upstream backend_servers {
    server app-server1:8080 max_fails=3 fail_timeout=30s;
    server app-server2:8080 max_fails=3 fail_timeout=30s;
    server app-server3:8080 max_fails=3 fail_timeout=30s;
}
```

**Giải thích:**
- `max_fails=3`: Sau 3 lần thất bại → đánh dấu DOWN
- `fail_timeout=30s`: Đợi 30s trước khi thử lại
- Kiểm tra passive (dựa trên response của actual requests)

### HAProxy (Active Health Check)

```haproxy
backend app_servers
    option httpchk GET /health
    http-check expect status 200
    server server1 app-server1:8080 check inter 2s rise 2 fall 3
```

**Giải thích:**
- `option httpchk GET /health`: Gửi GET request đến /health
- `inter 2s`: Kiểm tra mỗi 2 giây
- `rise 2`: Cần 2 lần success → UP
- `fall 3`: Cần 3 lần fail → DOWN

## 📊 So sánh Nginx vs HAProxy

| Tiêu chí | Nginx | HAProxy |
|----------|-------|---------|
| **Health Check** | Passive (basic) | Active (advanced) |
| **Monitoring** | Limited | Built-in stats page |
| **Web Server** | ✅ Yes | ❌ No |
| **Reverse Proxy** | ✅ Yes | ✅ Yes |
| **Load Balancer** | ✅ Yes | ✅ Yes (specialized) |
| **Configuration** | ⭐⭐⭐⭐⭐ Easy | ⭐⭐⭐⭐ Moderate |
| **Real-time Stats** | ❌ No (Nginx Plus) | ✅ Yes (built-in) |
| **HTTP/HTTPS** | ✅ Full support | ✅ Full support |
| **TCP/UDP** | ✅ Yes | ✅ Yes |

### Khi nào dùng gì?

**Chọn Nginx nếu:**
- ✅ Bạn cần web server + reverse proxy + load balancer trong 1
- ✅ Setup đơn giản, phổ biến
- ✅ Tích hợp tốt với static content serving

**Chọn HAProxy nếu:**
- ✅ Cần load balancer chuyên dụng với performance cao
- ✅ Cần monitoring chi tiết (stats page)
- ✅ Cần health check chủ động (active probing)

## 🎮 Load Balancing Algorithms

### 1. Round Robin (Mặc định)
```
Request 1 → Server 1
Request 2 → Server 2
Request 3 → Server 3
Request 4 → Server 1 (lặp lại)
```

### 2. Least Connections
```nginx
upstream backend {
    least_conn;
    server server1:8080;
    server server2:8080;
    server server3:8080;
}
```
Chọn server có ít active connections nhất.

### 3. IP Hash (Sticky Sessions)
```nginx
upstream backend {
    ip_hash;
    server server1:8080;
    server server2:8080;
    server server3:8080;
}
```
Client với cùng IP luôn được route đến cùng server.

### 4. Weighted
```nginx
upstream backend {
    server server1:8080 weight=3;
    server server2:8080 weight=2;
    server server3:8080 weight=1;
}
```
Server 1 nhận 50% traffic, Server 2 nhận 33%, Server 3 nhận 17%.

## 🛠️ Commands Cheat Sheet

### Docker
```bash
docker-compose up -d              # Start background
docker-compose down               # Stop all
docker-compose logs -f            # Follow logs
docker ps                         # List containers
docker stop <container>           # Stop container
docker start <container>          # Start container
docker restart <container>        # Restart container
```

### Test
```bash
curl http://localhost/            # Test request
curl http://localhost/health      # Health check
test-load-balance.bat             # Automated test

# Multiple requests
for /l %i in (1,1,10) do curl http://localhost/
```

### Nginx
```bash
nginx -t                          # Test config
nginx -s reload                   # Reload
nginx -s stop                     # Stop
```

### HAProxy
```bash
haproxy -f haproxy.cfg -c         # Test config
```

## 🐛 Troubleshooting

### Port already in use
```cmd
netstat -ano | findstr :80
taskkill /PID <pid> /F
```

### Docker issues
```bash
docker-compose down -v
docker system prune -a
docker-compose up --build
```

### Health check not working
1. Test endpoint: `curl http://localhost:8081/health`
2. Check logs: `docker logs availability-server1`
3. Verify network: `docker network inspect availability_availability-network`

## 📚 Tài liệu tham khảo

- [QUICKSTART.md](QUICKSTART.md) - Hướng dẫn nhanh cho Windows
- [README-NGINX.md](README-NGINX.md) - Chi tiết về Nginx
- [README-HAPROXY.md](README-HAPROXY.md) - Chi tiết về HAProxy
- [HELP.md](HELP.md) - Hướng dẫn tổng quan

### External Links
- [Nginx Documentation](https://nginx.org/en/docs/)
- [HAProxy Documentation](https://www.haproxy.org/docs/)
- [Docker Compose Reference](https://docs.docker.com/compose/)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/)

## 👨‍🎓 Tác giả

**IUH - Software Engineering**  
Kiến trúc Phần mềm - Lab  
Tuần 03 - Bài 02: High Availability with Load Balancer

---

**License:** MIT  
**Year:** 2026

## 🎓 Kiến thức đạt được

Sau khi hoàn thành lab này, bạn sẽ:

1. ✅ Hiểu rõ khái niệm Load Balancing và High Availability
2. ✅ Biết cách cấu hình Nginx/HAProxy làm load balancer
3. ✅ Triển khai Health Check và Auto Failover
4. ✅ Sử dụng Docker Compose để orchestrate microservices
5. ✅ Debug và monitor distributed systems
6. ✅ So sánh và lựa chọn giải pháp phù hợp cho production

**Happy Learning! 🚀**

