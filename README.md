# 🤖 Ollama AI Assistant

A modern, professional web-based chat interface for interacting with Ollama AI models. Built with Spring Boot and featuring a sleek, responsive frontend design.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen)
![Ollama](https://img.shields.io/badge/Ollama-AI-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📋 Table of Contents

- [Features](#-features)
- [Architecture](#-architecture)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [API Documentation](#-api-documentation)
- [Frontend Features](#-frontend-features)
- [Project Structure](#-project-structure)
- [Technologies Used](#-technologies-used)
- [Contributing](#-contributing)
- [Troubleshooting](#-troubleshooting)
- [License](#-license)

## ✨ Features

### Backend Features
- **RESTful API** for chat interactions
- **Ollama Integration** with configurable AI models
- **Spring Boot** architecture with dependency injection
- **CORS Configuration** for cross-origin requests
- **Error Handling** with proper HTTP status codes
- **JSON Request/Response** format

### Frontend Features
- **Modern UI Design** with gradient backgrounds and animations
- **Real-time Chat Interface** with message bubbles
- **Typing Indicators** with animated dots
- **Message Timestamps** for conversation tracking
- **Auto-resizing Input** that grows with content
- **Responsive Design** optimized for mobile and desktop
- **Professional Styling** with Inter font and consistent spacing
- **Error Handling** with user-friendly error messages
- **Clear Chat Functionality** to reset conversations

## 🏗️ Architecture

```
┌─────────────────┐    HTTP     ┌─────────────────┐    HTTP     ┌─────────────────┐
│   Frontend      │ ────────────▶│   Spring Boot   │ ────────────▶│     Ollama      │
│  (Thymeleaf)    │             │   Application   │             │   AI Service    │
│                 │◀────────────│                 │◀────────────│                 │
└─────────────────┘             └─────────────────┘             └─────────────────┘
        │                              │                              │
        │                              │                              │
   User Interface               REST API Layer               AI Model Processing
```

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

### Required Software
- **Java 17** or higher
- **Maven 3.6+** for dependency management
- **Ollama** installed and running locally

### Ollama Setup
1. Install Ollama from [official website](https://ollama.ai/)
2. Pull the required model:
   ```bash
   ollama pull deepseek-r1:latest
   ```
3. Verify Ollama is running on port 11434:
   ```bash
   curl http://localhost:11434/api/version
   ```

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone <your-repository-url>
cd olamaAi
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## ⚙️ Configuration

### AI Model Configuration
The application is configured to use the `deepseek-r1:latest` model by default. You can change this in the `OllamaService.java` file:

```java
request.put("model", "your-preferred-model");
```

### Available Configuration Options
- **Ollama API URL**: `http://localhost:11434/api/generate` (default)
- **Spring Boot Port**: `8080` (default)
- **AI Model**: `deepseek-r1:latest` (configurable)

### CORS Configuration
The application includes CORS configuration to allow requests from the frontend:
- **Allowed Origins**: `http://localhost:8080`
- **Allowed Methods**: `GET`, `POST`
- **Credentials**: Enabled

## 📖 Usage

### Accessing the Application
1. Start the Spring Boot application
2. Open your browser and navigate to: `http://localhost:8080/api/chat`
3. Start chatting with the AI assistant!

### Chat Features
- **Send Messages**: Type your message and press Enter or click Send
- **Multi-line Messages**: Use Shift+Enter for new lines
- **Clear Chat**: Click the trash icon to reset the conversation
- **Real-time Responses**: See typing indicators while AI processes your request

## 📚 API Documentation

### Endpoints

#### `GET /api/chat`
**Description**: Serves the chat interface
- **Response**: HTML page with chat interface

#### `POST /api/chat`
**Description**: Sends a message to the AI and receives a response

**Request Body**:
```json
{
  "prompt": "Your message here"
}
```

**Response**:
```json
{
  "response": "AI response here"
}
```

**Status Codes**:
- `200 OK`: Successful response
- `400 Bad Request`: Invalid request format
- `500 Internal Server Error`: Server or AI service error

### Example cURL Request
```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt": "Hello, how are you?"}'
```

## 🎨 Frontend Features

### User Interface Components
- **Header**: Professional branding with online status indicator
- **Chat Area**: Scrollable message container with animations
- **Message Bubbles**: Distinct styling for user and AI messages
- **Input Area**: Auto-resizing textarea with action buttons
- **Animations**: Smooth transitions and typing indicators

### Responsive Design
- **Desktop**: Full-width layout with optimal spacing
- **Tablet**: Adapted layout for medium screens
- **Mobile**: Stacked input controls and adjusted sizing

### Accessibility Features
- **Keyboard Navigation**: Full keyboard support
- **Focus Management**: Proper focus handling
- **Screen Reader**: Semantic HTML structure

## 📁 Project Structure

```
olamaAi/
├── src/
│   ├── main/
│   │   ├── java/com/example/olamaAi/
│   │   │   ├── OlamaAiApplication.java      # Main application class
│   │   │   ├── config/
│   │   │   │   └── WebConfig.java           # CORS and web configuration
│   │   │   ├── controller/
│   │   │   │   └── ChatController.java      # REST API endpoints
│   │   │   └── service/
│   │   │       └── OllamaService.java       # Ollama integration service
│   │   └── resources/
│   │       ├── templates/
│   │       │   └── chat.html                # Frontend chat interface
│   │       └── application.properties       # Application configuration
│   └── test/
├── pom.xml                                  # Maven dependencies
└── README.md                               # This file
```

## 🛠️ Technologies Used

### Backend Stack
- **Spring Boot 3.4.4** - Application framework
- **Spring Web** - REST API development
- **Spring Boot Thymeleaf** - Template engine
- **Jackson** - JSON processing
- **Lombok** - Code generation
- **Maven** - Dependency management

### Frontend Stack
- **HTML5** - Markup structure
- **CSS3** - Styling and animations
- **JavaScript (ES6+)** - Interactive functionality
- **Font Awesome** - Icons
- **Google Fonts (Inter)** - Typography

### External Services
- **Ollama** - AI model hosting and inference

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/amazing-feature`
3. **Commit your changes**: `git commit -m 'Add some amazing feature'`
4. **Push to the branch**: `git push origin feature/amazing-feature`
5. **Open a Pull Request**

### Development Guidelines
- Follow Java coding conventions
- Write meaningful commit messages
- Add tests for new features
- Update documentation as needed

## 🐛 Troubleshooting

### Common Issues

#### 1. Ollama Connection Error
**Problem**: Cannot connect to Ollama service
**Solution**: 
- Ensure Ollama is running: `ollama serve`
- Check if the model is available: `ollama list`
- Verify port 11434 is accessible

#### 2. Model Not Found
**Problem**: AI model not available
**Solution**:
- Pull the required model: `ollama pull deepseek-r1:latest`
- Check available models: `ollama list`

#### 3. CORS Issues
**Problem**: Frontend cannot access backend
**Solution**:
- Verify WebConfig.java CORS settings
- Ensure frontend is accessing the correct port (8080)

#### 4. Port Already in Use
**Problem**: Port 8080 is already occupied
**Solution**:
- Change the port in `application.properties`:
  ```properties
  server.port=8081
  ```
- Or stop the process using port 8080

### Logs and Debugging
- Check application logs for detailed error messages
- Use browser developer tools for frontend debugging
- Verify Ollama logs: `ollama logs`

## 🙏 Acknowledgments

- [Ollama](https://ollama.ai/) for providing the AI inference engine
- [Spring Boot](https://spring.io/projects/spring-boot) for the excellent framework
- [Font Awesome](https://fontawesome.com/) for the beautiful icons
- [Google Fonts](https://fonts.google.com/) for the Inter typeface

---

**Made with ❤️ using Spring Boot and Ollama**

For questions or support, please open an issue on GitHub.
