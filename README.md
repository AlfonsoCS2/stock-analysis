## StockAnalysis:
StockAnalysis is a stock market analysis tool built with Spring Boot. It retrieves real‑time financial data from the AlphaVantage API, computes technical indicators and generates a 0–100 predictive score that reflects the expected short‑term performance of a stock.

## Disclaimer:
This project does not provide financial advice and must not be used to make real investment decisions.
I assume no responsibility for any actions taken based on this software. The purpose of this project is purely educational and experimental, focused on technical analysis and software development.

## Features:
- Real‑time stock data retrieval via AlphaVantage
- Predictive scoring system (0–100) based on technical indicators: 
        Simple Moving Average (SMA)
        Exponential Moving Average (EMA)
        Relative Strength Index (RSI)

- In‑memory H2 database for testing


## Architecture Overview:
┌──────────────────────┐
│  MarketDataClient     │  → Fetches raw data from AlphaVantage
└───────────┬──────────┘
            │
┌───────────▼──────────┐
│  MarketDataService    │  → Cleans, transforms, and computes indicators
└───────────┬──────────┘
            │
┌───────────▼──────────┐
│ AnalisisService      │  → Generates the 0–100 score
└──────────────────────┘

## Scoring Logic:
The system produces a score between 0 and 100 by combining moving average trends, recent momentum, short‑term price variation and basic bullish/bearish signals.

## Interpretation:
80–100 → strong bullish trend
50–79 → moderate upward trend
20–49 → weak or sideways trend
0–19 → bearish trend

## Running the Project: 
    Requirements:
        - Java 17
        - Maven 3.8+
        - Internet connection (for API calls)

1. Set your AlphaVantage API key

Windows (PowerShell):
setx ALPHAVANTAGE_API_KEY "YOUR_REAL_KEY"

Linux/Mac:
export ALPHAVANTAGE_API_KEY="YOUR_REAL_KEY"

2. Run the application

mvn spring-boot:run

Configuration:
The application uses environment variables to avoid exposing API keys:

alphavantage.api.key=${ALPHAVANTAGE_API_KEY}
alphavantage.base.url=https://www.alphavantage.co/query

## Future Improvements:
- Implement historical backtesting
- Replace H2 with PostgreSQL
- Add caching to reduce API calls
- Build a frontend dashboard (React/Angular)
- Add user profiles and authentication

## License:
This project is distributed for educational purposes.
You are free to modify or extend it.