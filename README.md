# 🛡️ Authorization Service — Spring Boot + JWT + Role-Based Access Control

Authorization Service ini adalah backend berbasis **Spring Boot** yang mengimplementasikan:
- ✅ JWT Authentication (login tanpa session)
- ✅ Role-Based Access Control (RBAC)
- ✅ Dynamic API Authorization (dari database)
- ✅ Global Exception Handling dengan format response standar
- ✅ Swagger UI tanpa perlu token
- ✅ Clean Architecture dan Security Filter berlapis

---

## ⚙️ Tech Stack
| Komponen | Teknologi |
|-----------|------------|
| Framework | Spring Boot 3.x |
| Bahasa | Java 21 |
| Database | PostgreSQL |
| Security | Spring Security + JWT |
| Documentation | Swagger / OpenAPI |
| Build Tool | Maven |

---

## 🔐 Authentication & Authorization Flow

1. **User login** → sistem memverifikasi username/password.  
   Jika valid → menghasilkan **JWT Token** yang berisi `username` & `userId`.

2. **Setiap request API** membawa token lewat header
   
4. **JwtAuthFilter**:
- Memvalidasi token.
- Jika valid, menaruh `userId` di `HttpServletRequest`.

4. **RbacAuthorizationFilter**:
- Membaca `path` dan `method` dari request.
- Mengecek tabel `t_apis`:
  - Jika `is_auth = true` → hanya role tertentu yang bisa akses.
  - Jika `is_auth = false` → semua user bisa akses.
- Jika user tidak punya izin → response 403 (forbidden) dengan format standar.

---

## 🚀 API Overview

### 🟢 1. Whitelisted API (Tidak butuh token)
API ini dapat diakses tanpa autentikasi.

| Method | Endpoint | Deskripsi |
|---------|-----------|-----------|
| `POST` | `/api/auth/register` | Registrasi user baru |
| `POST` | `/api/auth/login` | Login dan mendapatkan JWT token |
| `GET` | `/swagger-ui/index.html` | Swagger UI |
| `GET` | `/v3/api-docs` | API Documentation |

---

### 🟡 2. Protected API (Butuh token, role tertentu)
API ini memerlukan **token JWT yang valid** dan **role tertentu** yang diizinkan di tabel `t_role_api`.

Contoh data tabel `t_apis`:

| id | path | method | is_auth | deskripsi |
|----|------|---------|----------|------------|
| 1 | `/api/v1/roles` | GET | `true` | Get all roles (role-specific) |
| 2 | `/api/v1/users` | GET | `true` | Get all users (role-specific) |
| 3 | `/api/v1/roles/{id}` | GET | `false` | Get role detail (public) |

Jika `is_auth = true` → sistem akan memeriksa apakah `role_id` dari user diizinkan di tabel `t_role_api`.

---

### 🔴 3. Response Format

Semua response mengikuti format **BaseResponse**:

#### ✅ Success
```json
{
"success": true,
"status": "SUCCESS",
"code": 200,
"message": "Data retrieved successfully",
"data": {
 "id": 1,
 "roleName": "ADMIN"
},
"timestamp": "2025-10-04T10:35:22.123456"
}
```
#### ❌ UNAUTHORIZED
```json
{
  "success": false,
  "status": "UNAUTHORIZED",
  "code": 401,
  "message": "Invalid or missing token",
  "timestamp": "2025-10-04T10:35:22.123456"
}
```
#### ❌ FORBIDDEN
```json
{
  "success": false,
  "status": "FORBIDDEN",
  "code": 403,
  "message": "You don't have access",
  "timestamp": "2025-10-04T10:35:22.123456"
}
```

---

## Role-Based Access Control (RBAC)
### 🗂️ Struktur Tabel
| Tabel         | Tujuan                          |
| ------------- | ------------------------------- |
| `t_users`     | Data user                       |
| `t_roles`     | Data role                       |
| `t_role_user` | Mapping user ↔ role             |
| `t_apis`      | Daftar endpoint                 |
| `t_role_api`  | Mapping role ↔ api (izin akses) |

### Relation Table
t_apis
id | path              | method | is_auth
---|--------------------|--------|---------
1  | /api/v1/roles     | GET    | true
2  | /api/v1/roles/*   | GET    | false

t_role_api
role_id | api_id
--------|--------
1       | 1



