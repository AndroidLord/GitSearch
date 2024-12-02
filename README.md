# GitSearch 📂🔍  

A powerful and intuitive mobile application to search, explore, and navigate through GitHub repositories and contributors. Built using **Kotlin**, **Jetpack Compose**, and the latest **Jetpack Libraries**, this app adheres to modern development practices with clean architecture and an MVVM pattern.

---

## Features 🌟  

### Home Screen  
- **Search Bar**: Effortlessly search for GitHub repositories using the GitHub API.  
- **Paginated Results**: Displays results in pages of 10 items using **Paging 3** library for smooth scrolling.  
- **Offline Support**: Saves the search results locally in **RoomDB** for offline access.  
- **Navigation**: Click on any repository to view its details on the Repo Details screen.

### Repo Details Screen  
- **Repository Information**: Displays image, name, project link, description, and a list of contributors.  
- **WebView Integration**: Opens the repository link in an embedded web browser for seamless navigation.  
- **Contributor Repositories**: Displays repositories of selected contributors.  

---

## Screenshots 🖼️  

<table>
  <tr>
    <td><b>Home Screen</b></td>
    <td><b>Search Results</b></td>
    <td><b>Repo Details</b></td>
    <td><b>WebView Project Link</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/AndroidLord/GitSearch/blob/master/photos/Home%20Search%20Page.png" alt="Home Search Page" width="200"/></td>
    <td><img src="https://github.com/AndroidLord/GitSearch/blob/master/photos/Android%20Search.png" alt="Android Search" width="200"/></td>
    <td><img src="https://github.com/AndroidLord/GitSearch/blob/master/photos/Repo%20Detail%20Page.png" alt="Repo Detail Page" width="200"/></td>
    <td><img src="https://github.com/AndroidLord/GitSearch/blob/master/photos/web%20view%20project%20link.png" alt="Web View Project Link" width="200"/></td>
  </tr>
</table>


---

## Demo 🎥  

Check out the app in action:  
[**Watch Demo Video**](https://drive.google.com/file/d/1N91I9ItHWdXe3wSJrM2m4aHo0Z3wRk73/view?usp=sharing)  

---

## APK Download 📲  

[Download GitSearch APK](https://github.com/AndroidLord/GitSearch/blob/master/apk/GitSearch.apk)  

---

## Tech Stack 🛠️  

- **Kotlin**: Modern, concise, and robust programming language.  
- **Jetpack Compose**: For building a modern, declarative UI.  
- **Paging 3**: For efficient and smooth pagination.
- **DI**: used Dagger Hilt for DI.
- **RoomDB**: For offline caching and local database management.  
- **Retrofit**: For reliable and efficient network calls.  
- **Coroutines and Flows**: For asynchronous programming and reactive data handling.  
- **MVVM Architecture**: Clean, scalable, and maintainable project structure.  
- **Navigation Component**: For seamless navigation between screens.  

---

## How It Works 🔍  

1. **Search Repositories**:  
   - Enter a query in the search bar to fetch repository data from GitHub API.  
   - The results are paginated, showing 10 items per page.  

2. **Offline Caching**:  
   - The results are saved locally using RoomDB, allowing offline access.  

3. **Repo Details Screen**:  
   - Displays comprehensive details about a selected repository.  
   - Includes a WebView for opening the repository's project link.  
   - Lists contributors with navigation to their associated repositories.  

---

## Setup Instructions ⚙️  

Follow these steps to run the project locally:  

1. **Clone the Repository**:  
   ```bash  
   git clone https://github.com/AndroidLord/GitSearch.git  
   cd GitSearch  
   ```  

2. **Build & Run**:  
   - Open the project in Android Studio.  
   - Build and run the app on your preferred Android device or emulator.  

---

## Deliverables 📦  

- **Source Code**: [GitHub Repository](https://github.com/AndroidLord/GitSearch)  
- **Demo Video**: [Drive Link](https://drive.google.com/file/d/1N91I9ItHWdXe3wSJrM2m4aHo0Z3wRk73/view?usp=sharing)  
- **Screenshots**: Available in the repository under `photos/`.  
- **APK**: [Download Link](https://github.com/AndroidLord/GitSearch/blob/master/apk/GitSearch.apk)  

---

## Highlights 🌟  

This project demonstrates:  
- Effective use of **Jetpack Compose** and **MVVM architecture**.  
- Efficient network management and offline-first design with **Retrofit**, **RoomDB**, and **Paging 3**.  
- Clean and maintainable code structure, adhering to modern Android development standards.  
