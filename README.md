# Entertainment Balance Tracker

## Compile and Run

### Using Command Line

1. Navigate to the project directory:

```bash
cd entertainment-balance-tracker
```

2. Compile all Java files:

```bash
javac -d bin src/com/entertainmenttracker/*.java src/com/entertainmenttracker/model/*.java src/com/entertainmenttracker/util/*.java src/com/entertainmenttracker/ui/*.java
```

3. Run the application:

```bash
java -cp bin com.entertainmenttracker.Main
```

### Alternative (Single Command)

```bash
cd src && javac com/entertainmenttracker/*.java com/entertainmenttracker/model/*.java com/entertainmenttracker/util/*.java com/entertainmenttracker/ui/*.java && java com.entertainmenttracker.Main
```

## Project Structure

```
src/
└── com/
    └── entertainmenttracker/
        ├── Main.java                    # Entry point
        ├── model/
        │   └── DailyEntry.java          # Data model for daily entries
        ├── util/
        │   ├── FileHandler.java         # File save/load operations
        │   └── BalanceCalculator.java   # Balance calculation logic
        └── ui/
            └── MainFrame.java           # Main GUI window
```

## Balance Status Logic

| Status                 | Condition                         |
| ---------------------- | --------------------------------- |
| 🟢 BALANCED            | Study time ≥ Entertainment time   |
| 🟠 ENTERTAINMENT HEAVY | Entertainment > Study (but < 70%) |
| 🔴 HIGH RISK           | Entertainment ≥ 70% of total time |
| 🔵 STUDY FOCUSED       | Study ≥ 70% of total time         |
