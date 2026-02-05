# Quick Start Guide - Windows

## 🚀 Khởi động nhanh nhất (Dùng Docker)

### Yêu cầu:
- Docker Desktop for Windows
- Git Bash hoặc PowerShell

### Bước 1: Chạy với Nginx
```bash
docker-compose up --build
```

### Bước 2: Kiểm tra
- Mở trình duyệt: http://localhost
- Refresh nhiều lần để thấy load balancing

### Bước 3: Test Health Check
```bash
# Mở terminal mới
docker stop availability-server1

# Refresh browser - chỉ thấy server 2 và 3
# Nginx tự động phát hiện server 1 chết

# Khởi động lại
docker start availability-server1

# Refresh browser - server 1 được đưa trở lại
```

### Xem Stats
```bash
# Logs của Nginx
docker logs availability-nginx

# Logs tất cả services
docker-compose logs -f
```

## 🎯 Alternative: Dùng HAProxy

```bash
docker-compose -f docker-compose-haproxy.yml up --build
```

### Truy cập:
- **Application**: http://localhost:8080
- **HAProxy Stats**: http://localhost:8404/stats
  - Username: admin
  - Password: admin

### HAProxy Stats Page cho phép bạn:
- ✅ Xem real-time status của từng server (UP/DOWN)
- ✅ Monitor số requests/responses
- ✅ Xem health check status
- ✅ Theo dõi connections
- ✅ Enable/Disable servers thủ công

## 📝 Chạy local (không Docker)

### Yêu cầu:
- Java 17+
- Maven
- MariaDB/MySQL
- Nginx hoặc HAProxy

### Bước 1: Setup Database
```sql
CREATE DATABASE availability_db;
```

### Bước 2: Chạy 3 servers
Mở 3 Command Prompt hoặc PowerShell:

**Window 1:**
```cmd
cd C:\Users\NHAN\4_nam_dai_hoc\ki_8_nam_2026_2027\KienTrucPhanMem\Solution_Lab\KienTrucPhanMem_Lab\Tuan03\Bai02\availability
start-server1.bat
```

**Window 2:**
```cmd
cd C:\Users\NHAN\4_nam_dai_hoc\ki_8_nam_2026_2027\KienTrucPhanMem\Solution_Lab\KienTrucPhanMem_Lab\Tuan03\Bai02\availability
start-server2.bat
```

**Window 3:**
```cmd
cd C:\Users\NHAN\4_nam_dai_hoc\ki_8_nam_2026_2027\KienTrucPhanMem\Solution_Lab\KienTrucPhanMem_Lab\Tuan03\Bai02\availability
start-server3.bat
```

### Bước 3: Chạy Nginx

1. Tải Nginx: http://nginx.org/en/download.html
2. Giải nén vào `C:\nginx`
3. Copy file config:
```cmd
copy nginx.conf C:\nginx\conf\nginx.conf
```
4. Chạy:
```cmd
cd C:\nginx
nginx.exe
```

### Bước 4: Test
```cmd
# Test với curl (cài Git Bash hoặc dùng PowerShell)
curl http://localhost/

# Hoặc chạy script test
test-load-balance.bat
```

## 🧪 Test Script

Chạy script tự động test load balancing:

```cmd
test-load-balance.bat
```

Script này sẽ gửi 20 requests liên tiếp và hiển thị server nào xử lý mỗi request.

## 🛠️ Lệnh thường dùng

### Docker Commands
```bash
# Start services
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs -f

# List containers
docker ps

# Stop specific server
docker stop availability-server1

# Start specific server
docker start availability-server1

# Restart all
docker-compose restart

# Clean up everything
docker-compose down -v
docker system prune -a
```

### Nginx Commands (Local)
```cmd
# Test config
cd C:\nginx
nginx.exe -t

# Start
nginx.exe

# Reload config
nginx.exe -s reload

# Stop
nginx.exe -s stop

# Quit gracefully
nginx.exe -s quit
```

### Test Commands
```cmd
# Single request
curl http://localhost/

# Multiple requests
for /l %i in (1,1,10) do @curl http://localhost/ & echo.

# Health check
curl http://localhost/health

# With PowerShell
1..10 | ForEach-Object { Invoke-WebRequest http://localhost/ }
```

## 📊 Monitor & Debug

### Xem logs Docker
```bash
# All services
docker-compose logs

# Follow logs
docker-compose logs -f

# Specific service
docker logs availability-server1
docker logs availability-nginx
docker logs availability-haproxy
```

### Xem Nginx logs (Local)
```cmd
# Access log
type C:\nginx\logs\access.log

# Error log
type C:\nginx\logs\error.log

# Tail logs (with Git Bash)
tail -f C:/nginx/logs/access.log
```

### Check ports
```cmd
# See what's using port 80
netstat -ano | findstr :80

# See what's using port 8081
netstat -ano | findstr :8081

# Kill process
taskkill /PID <process-id> /F
```

## 🔧 Troubleshooting

### Port already in use
```cmd
# Find process using port
netstat -ano | findstr :<port>

# Kill process
taskkill /PID <pid> /F
```

### Docker build fails
```bash
# Clean Docker
docker-compose down -v
docker system prune -a

# Rebuild
docker-compose up --build
```

### Can't connect to database
1. Check MariaDB is running: `docker ps`
2. Check connection string in application.properties
3. Wait for database to be ready (check logs)

### Server not detected as DOWN
1. Check health check endpoint works: `curl http://localhost:8081/health`
2. Verify Nginx/HAProxy config
3. Check timeout settings

## 📖 Đọc thêm

- **HELP.md**: Hướng dẫn tổng quan
- **README-NGINX.md**: Chi tiết về Nginx setup
- **README-HAPROXY.md**: Chi tiết về HAProxy setup

## 🎓 Học gì từ lab này?

✅ Load balancing với Nginx/HAProxy
✅ Health check tự động
✅ Failover và recovery
✅ High availability architecture
✅ Docker Compose orchestration
✅ Microservices deployment patterns
✅ Monitoring và debugging distributed systems

## 🆘 Cần giúp?

1. Kiểm tra logs: `docker-compose logs -f`
2. Verify tất cả containers đang chạy: `docker ps`
3. Test từng server riêng lẻ:
   - http://localhost:8081/health
   - http://localhost:8082/health
   - http://localhost:8083/health
4. Test load balancer:
   - Nginx: http://localhost/
   - HAProxy: http://localhost:8080/ và http://localhost:8404/stats

Good luck! 🚀

