# HotelReservationApplication
# 结构：
本项目包括3个包
- model
- service
- api
  
## Main Components of the App
The major components of the Hotel Reservation Application will consist of the following:

- CLI for the User Interface. We'll use the Command Line Interface (or CLI for the user interface. For this, we'll need to have Java monitor the CLI for user input, so the user can enter commands to search for available rooms, book rooms, and so on.
- Java code. The second main component is the Java code itself—this is where we add our business logic for the app.
- Java collections. Finally, we'll use Java collections for in-memory storage of the data we need for the app, such as the users' names, room availability, and so on.

## Application Architecture
The app will be separated into the following layers:

- User interface (UI), including a main menu for the users who want to book a room, and an admin menu for administrative functions.
- Resources will act as our Application Programming Interface (API) to our UI.
- Services will communicate with our resources, and each other, to build the business logic necessary to provide feedback to our UI.
- Data models will be used to represent the domain that we're using within the system (e.g., rooms, reservations, and customers).

![image](https://github.com/HappyFinch/HotelReservationApplication/assets/116667562/05155e37-9e71-4a4b-a205-f36e460f1890)

# command:
## 用户场景
该应用程序提供四种用户场景：
- 创建客户帐户。 用户需要先创建一个客户帐户，然后才能创建预订。

- 寻找房间。该应用程序应允许用户根据提供的入住和退房日期搜索可用房间。如果应用程序在指定的日期范围内有可用的房间，则会向用户显示相应房间的列表以供选择。

- 预订房间。用户选择房间后，该应用程序将允许他们预订房间并创建预订。

- 查看预订。预订房间后，该应用程序允许客户查看所有预订的列表。

## 管理场景
该应用程序提供四种管理方案：

- 显示所有客户帐户。
- 查看酒店的所有房间。
- 查看所有酒店预订。
- 向酒店应用程序添加房间。

## 预订房间 - 要求
该应用程序允许客户预订房间。以下是具体细节：

- 避免冲突的保留。在每个入住和退房日期范围内，单人间只能由一位顾客预订。
- 搜索推荐的房间。如果在客户的日期范围内没有可用的房间，将执行搜索以显示备选日期的推荐房间。推荐房间搜索会将原来的入住和退房日期增加 7 天，以查看酒店是否有空房，然后将推荐的房间/日期显示给客户。

示例：如果客户搜索日期范围为 1/1/2020 – 1/5/2020，并且所有房间都已预订，系统将使用日期范围 1/8/2020 - 1/12/2020 再次搜索推荐房间。如果没有推荐的房间，系统将不会返回任何房间。

## 房间要求
- 房费。客房将包含每晚的价格。显示房间时，付费房间将显示每晚的价格，免费房间将显示“免费”或价格为 0 美元。
- 唯一的房间号。每个房间都有一个唯一的房间号，这意味着任何两个房间都不能有相同的房间号。
- 房型。房间可以是单人或双人（枚举：单人、双人）。

## 客户要求
该应用程序将具有客户帐户。每个账户都有：
- 给客户的唯一电子邮件。RegEx 用于检查电子邮件的格式是否正确（即 name@domain.com）。
- 名字和姓氏。

出于本练习的目的，电子邮件 RegEx 很简单，可能无法涵盖所有​​现实世界中的有效电子邮件。例如，“name@domain.co.uk”不会被上述 RegEx 接受，因为它确实以“.com”结尾。如果您想尝试使您的 RegEx 更复杂，您可以——但对于本项目而言这不是必需的。

## 错误要求
酒店预订应用程序优雅地处理所有异常（包括用户输入），这意味着：

- 没有崩溃。应用程序不会根据用户输入而崩溃。
- 没有未处理的异常。该应用程序具有try用于catch捕获异常并向用户提供有用信息的块。没有未处理的异常。
