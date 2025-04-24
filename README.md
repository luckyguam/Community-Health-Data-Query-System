# 🏥 Community Health Data Query System

A modular, Java-based application designed to help users specify, store, and execute health-related database problems and queries through a JavaFX-powered interface. Built using Java, MySQL, and Maven, the system streamlines problem definition and solution management by dynamically linking user-defined issues with SQL contributions and executable queries.

---

## 📌 Overview

The **Community Health Data Query System** enables interactive database problem management by allowing users to:

- Specify health-related problems in plain English.
- Contribute and store SQL solutions to those problems.
- Dynamically execute saved queries with parameters.
- Manage and query data stored in a MySQL database.

The application follows a modular design using JavaFX for the user interface and JDBC for database communication. It is ideal for database education, prototyping health analytics, or exploring SQL-based solutions in a guided, user-friendly environment.

---

## 🧱 Technologies Used

| Component      | Description                            |
|----------------|----------------------------------------|
| Java           | Core language (JDK 1.8+)               |
| JavaFX         | UI framework (console-style interface) |
| MySQL          | Backend database system                |
| JDBC           | Database connectivity                  |
| Maven          | Build and dependency management        |

---

## 🧩 Features

- 🧠 **Problem Definition**  
  Users can define community health issues or analytical challenges to be addressed with SQL queries.

- 📥 **SQL Contributions**  
  Queries are stored in a structured way, linked to specific problem IDs for context and reuse.

- ▶️ **Runnable Queries**  
  Queries can include parameters, allowing for dynamic execution based on user input.

- 🛠️ **Extensible Architecture**  
  Modular class structure allows for future UI upgrades, query visualizations, or API integrations.

### ✅ Prerequisites

- Java 1.8+
- Maven
- MySQL Server
- JavaFX SDK

### 🖥️ Setup Instructions

1. **Clone the Repository**
```bash
git clone https://github.com/luckyguam/Community-Health-Data-Query-System.git
cd Community-Health-Data-Query-System
```

