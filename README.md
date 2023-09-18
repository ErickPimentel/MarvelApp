# MarvelApp
## Project Overview
MarvelApp is an Android application developed using MVVM (Model-View-ViewModel) and Clean Architecture. The project incorporates the best practices and technologies:

- **Minimum SDK Version:** 21
- **Design for Smaller Devices:** Designed to provide a consistent experience on smaller devices, ensuring responsive UI design.
- **Kotlin:** Implemented using the Kotlin programming language, known for its conciseness, safety, and interoperability with Java.
- **Coroutines:** Kotlin Coroutines are used to efficiently handle asynchronous operations within the application, enhancing responsiveness and performance.
- **Image Caching:** Makes effective use of the Glide library to implement image caching mechanism, optimizing image loading and display.
- **Error Handling:** Incorporates effective strategies for handling errors.
- **Design Patterns and Object-Oriented Practices:** The project follows established design patterns and object-oriented programming principles to ensure maintainability and extensibility.
- **Android Architecture Components (AAC):** Utilizes Google's Android Architecture Components for building a robust and well-structured app, utilizing the following key components:
  - **Lifecycle:** Manages the lifecycle of UI-related components, ensuring efficient management of activity and fragment lifecycles.
  - **ViewModel:** Stores and manages UI-related data, surviving configuration changes and ensuring data availability.
  - **LiveData:** Provides observable data to UI components, enabling real-time updates and synchronization between the UI and data sources.
  - **View Binding:** Simplifies view access and binding, enhancing code readability and eliminating view-related boilerplate code.
  - **Data Binding:** Allows for declarative UI layouts and minimizes the glue code necessary to bind application logic and layouts.
  - **Paging:** Facilitates loading large datasets gradually, improving app performance and responsiveness.
  - **Navigation:** Simplifies navigation implementation, providing a consistent and predictable user experience across the app.
- **Layouts and User Interface (UI) Optimization with Constraints:** Utilizes Constraints to create responsive and consistent user interfaces.
- **Dependency Injection:** Implements Dagger Hilt to optimize dependency management, enhancing modularity and maintainability of the application.

## Features
### Carousel displaying 5 characters
https://github.com/ErickPimentel/MarvelApp/assets/40219991/01f79b77-e88b-415d-b36d-ac2b0f2cb67e

### An infinite scrollable vertical list using Paging 3 displaying the following characters
https://github.com/ErickPimentel/MarvelApp/assets/40219991/883fee19-19d1-4e81-84f1-48fb04c4b0ca

### Search
https://github.com/ErickPimentel/MarvelApp/assets/40219991/9b7ca865-1a02-44d0-b169-ecaabae17aff

### Search suggestions
https://github.com/ErickPimentel/MarvelApp/assets/40219991/01614e12-62ad-4c6b-b715-fda5f59e248b

## How to Run the Project
To run the MarvelApp, follow these steps:

### 1. Clone the Repository:
- Clone this repository to your local machine using the following command:
```
git clone https://github.com/yourusername/MarvelApp-Android.git
```
- Replace `yourusername` with your GitHub username.
### 2. Configure API Authentication:
- Open the file `AuthInterceptor`.
- Locate the constants `PUBLIC_KEY` and `PRIVATE_KEY`.
- Replace them with your Marvel API public and private keys.
### 3. Run the MarvelApp:
- Build and run the MarvelApp
