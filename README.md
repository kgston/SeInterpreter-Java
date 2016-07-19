# TMS-SeInterpreter

Java-based standalone interpreter for Selenium Builder scripts.  
Forked from [https://github.com/SeleniumBuilder/SeInterpreter-Java]()

## Purpose

The purpose of the Selenium Interpreter is to parse a standard Selenium Builder 2 JSON script file such that it can be automatically be executed directly to a selenium webdriver or to a Selenium Remote Grid webdriver.

This way, test writers are able to leverage on the functionalities of Selenium Builder 2 - allowing for in-browser creation of test scripts while not having to write any native code to execute tests.

## Modifications

This fork of Selenium Interpreter includes the following changes

1. Addition of logic flow control - If, For and While loops
1. Ability to take fullscreen screenshots from unsupported browers

## How to build

1. Install Maven
    1. Ensure proxy settings have been set if applicable
1. In the root folder, run `mvn clean install` from the command line
