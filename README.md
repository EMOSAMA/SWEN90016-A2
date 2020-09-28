# SWEN90016-A2
## Requirements
### Admin Panel
1. Admin can login to system by initial username/email and password
2. Admin can add and mange services
3. Admin can view and manage all appointmemt here
### Customer Panel
1. Customers can register and login
2. Customers can add payment details
3. Customers can manage their personal details and payment details
3. Customers can make an appointment and a notification email will be send to hoster
4. Customers can cancel their appointment and also a related notification will be send to hoster
## Implementation
### Backend
* Golang
### Frontend
* Vue
### Database
* Postgresql
## API specification
### Login Function
1. Admin Login
```
POST: /login/admin
{
  email:""(String),
  password:"" (String)
}
```
2. Customer Login
```
POST: /login/user
{
  email:""(String),
  password:"" (String)
}
```
### Register Function
1. Customer Register
```
PUT: /register/user
{
  nanme:""(String),
  address:""(String),
  phone:""(String),
  email:""(String),
  password:""(String),
  comment:""(String)
}
```
### Detail Management
1. Personal Detail Update
```
POST: /user/personal
{
  nanme:""(String),
  address:""(String),
  phone:""(String),
  email:""(String),
  password:""(String),
  comment:""(String)
}
```
2. Biller Detail Update
```
POST: /user/biller
{
  nanme:""(String),
  address:""(String)
}
```
### Service Management
1. Avaliable Service
```
GET: /Service
```
2. Avaliable Service time
```
GET: /Service/serviceID
```
3. Service Create
```
PUT: /Service
{
  serviceName:""(String),
  cost:""(Float)
  duration:""(Float),
}
```
4. Service Update
```
POST: /Service
{
  serviceID:""(UUID),
  serviceName:""(String),
  cost:""(Float),
  duration:""(Float)
}
```
5. Service Delete
```
Delete: /Service
{
  serviceID:""(UUID)
}
```
### Appoinment Service
1. Appoinment View for Customer
```
POST: /appoinment/user
{
  userID:""(UUID),
  serviceID""(UUID),
  status""(String)
}
```
2. Appoinment View for Admin
```
POST: /appoinment/admin
{
  userName:""(String),
  serviceID""(UUID),
  status""(String)
}
```
3. Appoinment Create
```
PUT: /appoinment
{
  userID:""(UUID),
  serviceID:""(UUID),
  message:""(String),
  time:""(DateTime),
  location:""(String)
}
```
4. Appoinment Cancel
```
POST: /appoinment/cancel
{
  appointmentID:""(UUID)
}
```
## Database Table
1. Admin
```
  UUID admin_id;
  String name;
  String email;
  String password;
```
2. User
```
  UUID user_id;
  String nanme;
  String address;
  String phone;
  String email;
  String password;
  String comment;
```
3. Service
```
  UUID service_id;
  String service_nanme;
  Float cost;
  Float duration;
```
4. Appointment
```
  UUID appointment_id;
  UUID user_id;
  UUID service_id;
  DateTime time;
  String location;
  String status;
  String message;
```
