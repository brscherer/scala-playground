# Vending Machine Project

This project is a Scala-based application that simulates a vending machine. It provides an API to interact with the vending machine, allowing users to purchase items, check inventory, and manage the machine.

## API Routes

### Get Stock
- **Endpoint:** `/vending-machine/stock`
- **Method:** GET
- **Description:** Retrieves the current stock of the vending machine.
- **Response:**
  ```json
  [
    {
      "item": {
        "id": "1",
        "name": "Coke",
        "price": 1.25
      },
      "quantity": 8
    },
    ...
  ]
  ```

### Sell Item
- **Endpoint:** `/vending-machine/sell-item`
- **Method:** POST
- **Description:** Sell an item from the vending machine.
- **Request Body:**
  ```json
  {
    "itemId": "1",
    "quantity": 2
  }
  ```
- **Response:**
  ```json
  {
    "itemId": "2",
    "quantity": 9,
    "date": "2025-02-11"
  }
  ```

### Get Sales
- **Endpoint:** `/vending-machine/sales`
- **Method:** GET
- **Description:** Retrieve the sales report from the vending machine.
- **Response:**
  ```json
  [
    {
      "itemId": "1",
      "quantity": 2,
      "date": "2025-02-11"
    },
    ...
  ]
  ```

## Running the Application

To run the vending machine application, follow these steps:

1. **Clone the repository:**
   ```sh
   git clone https://github.com/brscherer/scala-playground.git
   cd scala-playground/vending-machine
   ```

2. **Build the project:**
   ```sh
   sbt compile
   ```

3. **Run the application:**
   ```sh
   sbt run
   ```

4. **Access the API:**
   Open your browser or use a tool like `curl` or Postman to interact with the API at `http://localhost:8080/`.

Enjoy using the vending machine application!