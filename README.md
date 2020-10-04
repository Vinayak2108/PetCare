# PetCare

PetCare is developed as a coding challenge, a part of the recruitment process at Syncechron.

## Problem Statement

A small veterinary clinic is in need of an application to allowtheir users to contact them easily and for them to provide information to theirusers about their pets. Implement this app with the following requirements: 
* Fetch the config.json to check for a chat and call featureavailability and work hours
* Call and/or chat buttons are at the top (see wireframe)
* Office hours are displayed below the call and chat buttons
* If both the chat and call features are disabled, do not show thecorresponding buttons
* Tapping on either button should display an alert with thefollowing message
  1) if within work hours: “Thankyou for getting in touch with us. We’ll get back to you as soon as possible”
  2) if outside work hours: “Workhours have ended. Please contact us again on the next workday”
* Fetch the pets.json for pet information to display on the screen
* Each pet information is presented in a cell with Image and title
* Tapping on a pet info cell would open a new screen that loads the pet information on a webview
* Views should adjust to orientation and screen size accordingly

## Points to be consider
* Customised Configuration for Weekday in working hr is not allowed due to multiple similar initials
* Time must be two-digit and 24 hr format
* Disk Catch for images are not implemented due to lack of unique ids for Pet data
