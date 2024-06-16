The assignment requires me to create a backend for an application that allows users to manage profiles and fetch cryptocurrency data from a third-party API. Here’s a breakdown of the requirements:

# Requirements

## User Profile Management:

- Sign up: Users should be able to sign up with a unique username and password.
- Login: Users should be able to log in using their username and password.
- Update Profile: Users should be able to update their profile information.
  Profile Fields: First Name, Last Name, Email, Mobile, Username, Password (Email should be unique).

## Fetch Cryptocurrency Data:
  Users should be able to search for cryptocurrency details from a third-party API (CoinMarketCap),using the provided API key in the request header.
  The API endpoint for fetching data is `https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=BTC,ETH,LTC`

## Validation and Security:
  Implement basic validation and secure API endpoints, which is as follows:
  
     Username: Only characters and digits, length 4 to 15.
  
     Password: Length 8 to 15, with at least 1 upper case, 1 lower case, 1 digit, and 1 special character.
