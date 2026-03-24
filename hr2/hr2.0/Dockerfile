# 前端构建阶段
FROM node:18-alpine as frontend-build
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ .
RUN npm run build

# 后端构建阶段
FROM maven:3.9-eclipse-temurin-17 as backend-build
WORKDIR /app/backend
COPY backend/pom.xml ./
RUN mvn dependency:go-offline
COPY backend/ .
RUN mvn package -DskipTests

# 最终运行阶段
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 复制前端构建结果
COPY --from=frontend-build /app/frontend/dist /app/frontend

# 复制后端构建结果
COPY --from=backend-build /app/backend/target/hr-backend-1.0.0.jar /app/backend/hr-backend.jar

# 暴露端口
EXPOSE 8080

# 启动命令
CMD ["java", "-jar", "/app/backend/hr-backend.jar"]