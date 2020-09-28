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
## API Specification
### Login Function
1. Admin Login
```
**Send:**
POST: /login/admin
{
  email:""(String),
  password:"" (String)
}
```
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    adminID:""(String)
  },
  message:""(String)
}
```
2. Customer Login
```
**Send:**
POST: /login/user
{
  email:""(String),
  password:"" (String)
}
```
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    userID:""(String)
  },
  message:""(String)
}
```
### Register Function
1. Customer Register
```
**Send:**
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
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    userID:""(String)
  },
  message:""(String)
}
```
### Detail Management
1. Personal Detail Update
```
**Send:**
POST: /user/personal
{
  userID:""(UUID),
  nanme:""(String),
  address:""(String),
  phone:""(String),
  email:""(String),
  password:""(String),
  comment:""(String)
}
```
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    userID:""(UUID),
    nanme:""(String),
    address:""(String),
    phone:""(String),
    email:""(String),
    password:""(String),
    comment:""(String)
  },
  message:""(String)
}
```
2. Biller Detail Update
```
**Send:**
POST: /user/biller
{
  billerID:""(UUID),
  nanme:""(String),
  address:""(String)
}
```
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    billerID:""(UUID),
    nanme:""(String),
    address:""(String)
  },
  message:""(String)
}
```
### Service Management
1. Avaliable Service
```
**Send:**
GET: /Service
```
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    serviceList:[](list)
  },
  message:""(String)
}
```
2. Avaliable Service time
```
**Send:**
GET: /Service/serviceID
```
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    avaliableTime:[](list)
  },
  message:""(String)
}
```
3. Service Create
```
**Send:**
PUT: /Service
{
  serviceName:""(String),
  cost:""(Float),
  duration:""(Float)
}
```
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    serviceID:""(UUID),
    serviceName:""(String),
    cost:""(Float),
    duration:""(Float)
  },
  message:""(String)
}
```
4. Service Update
```
**Send:**
POST: /Service
{
  serviceID:""(UUID),
  serviceName:""(String),
  cost:""(Float),
  duration:""(Float)
}
```
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    serviceID:""(UUID),
    serviceName:""(String),
    cost:""(Float),
    duration:""(Float)
  },
  message:""(String)
}
```
5. Service Delete
```
**Send:**
Delete: /Service
{
  serviceID:""(UUID)
}
```
```
**Return:**
{
  status:0,
  success:True,
  Data: null,
  message:""(String)
}
```
### Appoinment Service
1. Appoinment View for Customer
```
**Send:**
POST: /appoinment/user
{
  userID:""(UUID),
  serviceID""(UUID),
  status""(String)
}
```
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    appointmentList:[](list)
  },
  message:""(String)
}
```
2. Appoinment View for Admin
```
**Send:**
POST: /appoinment/admin
{
  userName:""(String),
  serviceID""(UUID),
  status""(String)
}
```
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    appointmentList:[](list)
  },
  message:""(String)
}
```
3. Appoinment Create
```
**Send:**
PUT: /appoinment
{
  userID:""(UUID),
  serviceID:""(UUID),
  message:""(String),
  time:""(DateTime),
  location:""(String)
}
```
```
**Return:**
{
  status:0,
  success:True,
  Data:{
    userID:""(UUID),
    serviceID:""(UUID),
    message:""(String),
    time:""(DateTime),
    location:""(String)
  },
  message:""(String)
}
```
4. Appoinment Cancel
```
**Send:**
POST: /appoinment/cancel
{
  appointmentID:""(UUID)
}
```
```
**Return:**
{
  status:0,
  success:True,
  Data:null
  message:""(String)
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
3. Biller
```
  UUID biller_id;
  UUID user_id;
  String name;
  String email;
```
4. Service
```
  UUID service_id;
  String service_nanme;
  Float cost;
  Float duration;
```
5. Appointment
```
  UUID appointment_id;
  UUID user_id;
  UUID service_id;
  DateTime time;
  String location;
  String status;
  String message;
```
