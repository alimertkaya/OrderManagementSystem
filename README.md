# 🛒 Order Management System

A simple desktop-based order management system built with Java Swing and MySQL.

## 🚀 Features
- **User Login:** Secure login screen with email and password validation.
- **Customer Management:**
  - Add, update, and delete customers
  - Search and filter customers by name or type
- **Product Management:**
  - Add, update, and delete products
  - Track product stock levels
  - Filter products by name, code, or stock status
- **Basket System:**
  - Add products to a temporary basket before placing an order.
- **Order Creation:**
  - Place orders for customers using selected products.
- **Validations:**
  - All input fields are validated (e.g., email format, empty fields).
- **Popup Menus:**
  - Right-click context menus for update/delete actions.

## 🧱 Technologies Used
- **Java (Swing):** Desktop UI development
- **MySQL:** Relational database
- **JDBC:** Java Database Connectivity
- **MVC Architecture:** Separation of concerns
- **DAO Pattern:** Abstracted database access
- **Singleton Pattern:** Ensures a single instance of DB connection

## 🖼️ Screenshots
> Login Frame
<p align="left">
  <img src = "assets/login.png" width="400"/>
</p>

> Customer Frame
<p align="left">
  <img src="assets/customer.png" width="800" height="400"/><br>
  <img src="assets/customerAdd.png" width="800" height="400"/>
</p>
<br>

> Product Frame
<p align="left">
  <img src="assets/product.png" width="800" height="400"/><br>
  <img src="assets/productPopup.png" width="800" height="400"/>
</p>
<br>

> Basket Frame
<p align="left">
  <img src="assets/createOrder.png" width="800" height="400"/><br>
  <img src="assets/createOrderAdd.png" width="800" height="400"/>
</p>
<br>

> Cart Frame
<p align="left">
  <img src="assets/cart.png" width="800" height="400"/>
</p>
