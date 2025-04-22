# Scala Play CRUD App

This is a basic Scala 3 web application built using the [Play Framework](https://www.playframework.com/).  
The purpose of this project is to implement simple CRUD functionality using in-memory data structures.

## Features

- ✅ Scala 3
- ✅ Play Framework
- ✅ Project structure ready for extension

## Project Structure

.
├── app
│   ├── controllers
│   └── views
├── build.sbt
├── build.sc
├── conf
│   ├── application.conf
│   ├── logback.xml
│   ├── messages
│   └── routes
├── logs
│   └── application.log
├── project
│   ├── build.properties
│   ├── metals.sbt
│   ├── plugins.sbt
│   ├── project
│   └── target
├── public
│   ├── images
│   ├── javascripts
│   └── stylesheets
├── target
│   ├── global-logging
│   ├── scala-3.6.4
│   ├── streams
│   ├── task-temp-directory
│   └── web
└── test
    └── controller  


## Requirements

- JDK 17+
- sbt

## How to Run

```bash
sbt run
