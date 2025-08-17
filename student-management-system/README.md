# Student Management System

A comprehensive Spring Boot application for managing educational institutions with role-based access control for students, teachers, and administrators.

## 🚀 Features

### Core Features
- **User Management**: Complete user registration and authentication system
- **Role-Based Access Control**: Different interfaces for Students, Teachers, and Administrators
- **Student Management**: Track student information, grades, and academic progress
- **Teacher Management**: Manage teacher profiles and assignments
- **Academic Tracking**: Monitor GPA, credits, and enrollment status
- **Responsive Design**: Modern UI that works on all devices

### Security Features
- **Spring Security**: Basic authentication and authorization
- **Password Encryption**: BCrypt password hashing
- **Session Management**: Secure session handling
- **Input Validation**: Comprehensive form validation

### Technical Features
- **Spring Boot 3.2.0**: Latest Spring Boot framework
- **Spring Data JPA**: Database operations with Hibernate
- **MySQL Database**: Relational database for data persistence
- **Thymeleaf Templates**: Server-side templating engine
- **Bootstrap 5**: Modern responsive UI framework

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.2.0, Spring Security, Spring Data JPA
- **Database**: MySQL 8.0
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome
- **Build Tool**: Maven
- **Java Version**: 17

## 📋 Prerequisites

Before running this application, make sure you have:

- **Java 17** or higher
- **Maven 3.6** or higher
- **MySQL 8.0** or higher
- **IntelliJ IDEA** (recommended) or any Java IDE

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd student-management-system
```

### 2. Database Setup
1. Create a MySQL database:
```sql
CREATE DATABASE student_management;
```

2. Update database configuration in `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Run the Application

#### Option 1: Using Maven
```bash
mvn spring-boot:run
```

#### Option 2: Using IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. Wait for Maven to download dependencies
3. Run `StudentManagementSystemApplication.java`

#### Option 3: Using JAR file
```bash
mvn clean package
java -jar target/student-management-system-0.0.1-SNAPSHOT.jar
```

### 4. Access the Application
Open your browser and navigate to: `http://localhost:8080`

## 👥 User Roles

### 1. Administrator (ADMIN)
- **Full System Access**: Manage all users, students, and teachers
- **User Management**: Create, update, delete, and activate/deactivate users
- **System Statistics**: View comprehensive system analytics
- **Data Management**: Access to all system data

### 2. Teacher (TEACHER)
- **Student Management**: View and manage assigned students (advisees)
- **Grade Management**: Update student grades and academic records
- **Profile Management**: Update personal information
- **Student Search**: Search and view student information

### 3. Student (STUDENT)
- **Personal Profile**: View and update personal information
- **Academic Records**: View grades, GPA, and academic progress
- **Schedule Access**: View class schedules and assignments
- **Password Management**: Change account password

## 📁 Project Structure

```
student-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/example/studentmanagementsystem/
│   │   │   ├── config/                 # Configuration classes
│   │   │   ├── controller/             # MVC Controllers
│   │   │   ├── entity/                 # JPA Entities
│   │   │   ├── repository/             # Data Access Layer
│   │   │   ├── service/                # Business Logic Layer
│   │   │   └── StudentManagementSystemApplication.java
│   │   └── resources/
│   │       ├── templates/              # Thymeleaf templates
│   │       └── application.properties  # Application configuration
│   └── test/                           # Test files
├── pom.xml                             # Maven configuration
└── README.md                           # Project documentation
```

## 🔧 Configuration

### Database Configuration
Update `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/student_management?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Server Configuration
```properties
# Server port (default: 8080)
server.port=8080

# Session timeout (default: 30 minutes)
server.servlet.session.timeout=30m
```

## 🗄️ Database Schema

The application automatically creates the following tables:

- **users**: User accounts and authentication
- **students**: Student information and academic records
- **teachers**: Teacher information and assignments
- **user_roles**: Role-based access control

## 🔐 Security

### Authentication
- Form-based login with Spring Security
- BCrypt password encryption
- Session-based authentication

### Authorization
- Role-based access control (ADMIN, TEACHER, STUDENT)
- URL-based security rules
- Method-level security annotations

## 🎨 UI/UX Features

- **Responsive Design**: Works on desktop, tablet, and mobile
- **Modern Interface**: Bootstrap 5 with custom styling
- **Interactive Elements**: Hover effects and smooth transitions
- **User-Friendly Navigation**: Intuitive menu structure
- **Flash Messages**: Success and error notifications

## 📊 Key Features Explained

### 1. User Registration
- Multi-step registration process
- Role selection during registration
- Email and username validation
- Password strength requirements

### 2. Dashboard
- Role-specific dashboards
- Quick statistics and overview
- Navigation to key features
- Recent activity display

### 3. Student Management
- Complete student profiles
- Academic record tracking
- GPA calculation
- Enrollment status management

### 4. Teacher Management
- Teacher profile management
- Student assignment (advisees)
- Grade management capabilities
- Department and specialization tracking

## 🧪 Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run with coverage
mvn test jacoco:report
```

## 🚀 Deployment

### Production Deployment
1. Update `application.properties` for production database
2. Set `spring.profiles.active=prod`
3. Build the application: `mvn clean package`
4. Deploy the JAR file to your server

### Docker Deployment
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/student-management-system-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new features
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

If you encounter any issues:

1. Check the application logs
2. Verify database connectivity
3. Ensure all prerequisites are met
4. Create an issue in the repository

## 🔄 Version History

- **v1.0.0**: Initial release with basic functionality
- **v1.1.0**: Added advanced search and filtering
- **v1.2.0**: Enhanced security features
- **v1.3.0**: Improved UI/UX and responsive design

## 📞 Contact

For questions or support, please contact:
- Email: support@studentmanagementsystem.com
- GitHub Issues: [Create an issue](https://github.com/your-repo/issues)

---

**Happy Coding! 🎓**