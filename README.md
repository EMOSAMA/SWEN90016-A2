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
#### *Admin Login
```
POST: /login/admin
{
  username:""(String),
  password:"" (String)
}
```
#### *Customer Login
```
POST: /login/user
{
  username:""(String),
  password:"" (String)
}
```
### Register Function
#### *Customer Register
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
#### *Personal Detail Update
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
#### *Biller Detail Update
```
POST: /user/biller
{
  nanme:""(String),
  address:""(String)
}
```
### Service Management
#### *Avaliable Service
```
GET: /Service
```
#### *Avaliable Service time
```
GET: /Service/serviceID
```
#### *Service Create
```
PUT: /Service
{
  serviceName:""(String),
  cost:""(Float)
  duration:""(Float),
}
```
#### *Service Update
```
POST: /Service
{
  serviceID:""(UUID),
  serviceName:""(String),
  cost:""(Float),
  duration:""(Float)
}
```
#### *Service Delete
```
Delete: /Service
{
  serviceID:""(UUID)
}
```
### Appoinment Service
#### *Appoinment View for Customer
```
POST: /appoinment/user
{
  userID:""(UUID),
  serviceID""(UUID),
  status""(String)
}
```
#### *Appoinment View for Admin
```
POST: /appoinment/admin
{
  userName:""(String),
  serviceID""(UUID),
  status""(String)
}
```
#### *Appoinment Create
```
PUT: /appoinment
{
  userID:""(UUID),
  serviceID:""(UUID),
  message:""(String),
  time:""(DateTime)
}
```
#### *Appoinment Cancel
```
POST: /appoinment/cancel
{
  appointmentID:""(UUID)
}
```


