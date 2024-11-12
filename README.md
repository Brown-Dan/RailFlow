# RailFlow 🚂

**RailFlow** is a RESTful JSON API wrapper that integrates with National Rail's XML-based data feeds and API. With it, you can easily access, manage, and monitor key rail data in real-time.

## 🚧 **TODO: 11th November - 19th November** 🚧

### 1. **Download Darwin Timetable Files 📅**
   - Add the ability to automaticlaly **download Darwin Timetable files** based on updates via the Darwin datafeed.

### 2. **Live Datafeed Timetable Updates ⚡**
   - Implement functionality to **update Darwin Timetable** in real-time with live data feeds.

### 3. **Route Monitoring for Delays & Cancellations 🚨**
   - Add the ability to **monitor specific routes** for **delays and cancellations**.
   - **Integrate with Twilio** for sending **SMS alerts** about delays and cancellations.

### 4. **Integrate with KnowledgeBase ActiveMQ Topic 🔗**
   - Integrate **KnowledgeBase ActiveMQ** topic

### 5. Expand Model using external data. 
    - Map Route Cancelation/Delay codes to their String format 
    - Add extra `TIPLOC` Mapping.

---
# **Example RailFlow API Documentation**

## **Endpoint: Search for Trains**

**GET** `https://railflow.co.uk/search?origin=HDF&destination=MAN`

- **Description**: Returns a list of possible trains between the specified `origin` and `destination` for the upcoming days.

### **Query Parameters**
| Parameter     | Type   | Description                                                  |
|---------------|--------|--------------------------------------------------------------|
| `origin`      | String | The starting station code (e.g., `HDF` for Huddersfield).    |
| `destination` | String | The destination station code (e.g., `MAN` for Manchester).   |

### **Example Request**
http
GET https://railflow.co.uk/search?origin=HDF&destination=MAN

## **Endpoint: Register for Notifications**

**POST** `https://railflow.co.uk/register/{journeyId}`

- **Description**: Registers a user for journey notifications for a specific journey.

### **Path Parameters**
| Parameter   | Type   | Description                                   |
|-------------|--------|-----------------------------------------------|
| `journeyId` | String | Unique ID of the journey to register for.     |

### **Request Body**
| Field                | Type   | Description                                                                  |
|----------------------|--------|------------------------------------------------------------------------------|
| `notificationMethod` | Enum   | Notification method: `SMS`, `EMAIL`, `WHATSAPP`, `RCS`, `FACEBOOK`, etc.     |
| `notificationAddress`| String | Contact address for notifications (e.g., `+44733994349`, `john@doe.com`).    |
| `startPeriod`        | String | Start of the notification period in `yyyy-MM-dd:HH:mm` format.               |
| `endPeriod`          | String | End of the notification period in `yyyy-MM-dd:HH:mm` format.                 |

### **Example Request**
```http
POST https://railflow.co.uk/register/12345
Content-Type: application/json

{
   "notificationMethod": "SMS",
   "notificationAddress": "+44733994349",
   "startPeriod": "2024-11-11:01:00",
   "endPeriod": "2024-12-12:02:00"
}
