# bsse-2020-flt
Formal Language Theory @ SPBU MSE home assignments

[![Build Status](https://travis-ci.org/vsalavatov/bsse-2020-flt.svg?branch=master)](https://travis-ci.org/vsalavatov/bsse-2020-flt.svg?branch=master)

## Prerequisites 
* openjdk11

## How to build and run
* build:
  ```bash
  $ gradlew build
  ```
* test:
  ```bash
  $ gradlew cleanTest test
  ```  
* run:
  ```bash
  $ gradlew installDist
  ``` 
  Executables are located in `{task}/build/install/{task}/bin`
  
## Query Language
See [specification](/QueryLanguage.md).

## Author
Vadim Salavatov <xremmargorpx@gmail.com>
