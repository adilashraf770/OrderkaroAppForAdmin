# Order Karo - Food Ordering App with Admin Panel

<img src="https://res.cloudinary.com/dc1uxxvox/image/upload/v1721302179/github/orderkaroappadmin2.png" alt="Order Karo"  >

## Description

Order Karo is an intricately designed native Android application built with Kotlin, aimed at delivering a smooth and efficient food ordering experience complemented by an integrated admin panel. At its core, the app leverages Firebase authentication to ensure secure and seamless sign-in and sign-up processes, bolstered by a visually appealing splash screen that enhances user engagement from the outset.

For users, Order Karo offers a user-friendly interface with essential features such as browsing food menus, adding items to carts, and placing orders seamlessly. Meanwhile, administrators benefit from robust functionalities including the ability to manage pending and completed orders, monitor earnings over time, add new menu items to the Firebase Realtime Database, and oversee user interactions within the app. The admin panel also supports profile management and the creation of additional admin accounts, enhancing operational flexibility and security.

All data, including user details, order histories, menu items, and administrative actions, are securely stored and synchronized in real-time via Firebase Realtime Database. This ensures consistent and reliable access to information for both users and administrators, fostering a cohesive and responsive food ordering and management system. Order Karo stands out not only for its technical prowess but also for its focus on delivering a seamless and intuitive experience tailored to meet the needs of both end-users and administrators alike.

### User Features:
- **Home**: Browse an extensive menu of food items.
- **Cart**: Add items to the cart and proceed to checkout.
- **Search**: Find specific food items quickly.
- **History**: View past orders and details.
- **Profile**: Manage personal information and settings.

### Admin Features:
- **Pending Orders**: View all pending orders from users.
- **Complete Orders**: Track all completed orders.
- **Earnings**: Monitor total earnings over time.
- **Add Menu Items**: Add new items to the menu stored in Firebase Realtime Database with storage.
- **Update Profile**: Admins can update their profile information.
- **Create New Admin**: Existing admins can create new admin accounts.
- **Order Management**: Accept and dispatch user orders, and handle payments.

All data, including user details, admin information, orders, and earnings, are stored in Firebase Realtime Database, ensuring real-time synchronization and reliability.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Authentication](#authentication)
- [User Features](#user-features)
- [Admin Features](#admin-features)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)
- [Contact](#contact)
- [Download](#download)

## Installation
1. Clone the repository:
    ```sh
    git clone  https://github.com/adilashraf770/OrderkaroAppForAdmin.git
    ```
2. Open the project in Android Studio.
3. Build the project to download all dependencies.
4. Set up Firebase in your project:
    - Go to the [Firebase Console](https://console.firebase.google.com/).
    - Create a new project or use an existing one.
    - Add an Android app to your Firebase project.
    - Download the `google-services.json` file and place it in the `app` directory of your project.
    - Add the Firebase SDK dependencies to your `build.gradle` files as instructed.

## Usage
1. Run the app on an emulator or a physical device.
2. Sign up or log in using the authentication feature.
3. Users can browse the home screen, search for food items, add items to the cart, view order history, and manage their profile.
4. Admins can view pending and completed orders, monitor earnings, add menu items, update profiles, create new admins, and manage user orders.

## Configuration
- Update the `google-services.json` file with your Firebase project credentials.
- Modify the Firebase rules as needed for your application.

## Authentication

<img src="https://res.cloudinary.com/dc1uxxvox/image/upload/v1721299971/github/orderkarouserapp3.png" alt="Authentication Banner" height="600" >

- The app uses Firebase Authentication for sign in, sign up, and splash screen.
- Ensure the Firebase Authentication methods are enabled in the Firebase Console.

## User Features
- **Home**: Browse an extensive menu of food items.
- **Cart**: Add items to the cart and proceed to checkout.
- **Search**: Find specific food items quickly.
- **History**: View past orders and details.
- **Profile**: Manage personal information and settings.

## Admin Features
- **Pending Orders**: View all pending orders from users.
- **Complete Orders**: Track all completed orders.
- **Earnings**: Monitor total earnings over time.
- **Add Menu Items**: Add new items to the menu stored in Firebase Realtime Database with storage.
- **Update Profile**: Admins can update their profile information.
- **Create New Admin**: Existing admins can create new admin accounts.
- **Order Management**: Accept and dispatch user orders, and handle payments.

## Contributing
1. Fork the repository.
2. Create a new branch for your feature or bug fix:
    ```sh
    git checkout -b feature-name
    ```
3. Commit your changes:
    ```sh
    git commit -m "Add some feature"
    ```
4. Push to the branch:
    ```sh
    git push origin feature-name
    ```
5. Open a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements
- [Firebase](https://firebase.google.com/) for providing the backend services.
- [Android Developers](https://developer.android.com/) for the documentation and tools.

## Contact
If you have any questions or suggestions, feel free to contact me at [smadal770@gmail.com].

## Download
You can download the APK file from the following link:
[Order Karo APK](https://drive.google.com/file/d/1JoKfYkWasPDExaioGXVzPF70-qSpK47-/view?usp=sharing)
