# USE CASE: Generate Country Reports

## CHARACTERISTIC INFORMATION

### Goal in Context

*As a Data Analyst, I want all countries in the world organized by largest population to smallest so that I can get a global population overview.*

*As a Data Analyst, I want all countries in a continent organized by largest population to smallest so that I can analyze population distribution in that continent.*

*As a Data Analyst, I want all countries in a region organized by largest population to smallest so that I can analyze population regionally.*

*As a Data Analyst, I want the top N populated countries in the world so that I can focus on largest global countries.*

*As a Data Analyst, I want the top N populated countries in a continent so that I can focus on major countries continent-wide.*

*As a Data Analyst, I want the top N populated countries in a region so that I can study most populated countries in that region.*

### Scope

*Covering world, continent, region, and capital levels*

### Level

*Primary task*

### Preconditions

*The database contains the population data of each country and correct geographic classification.*

*The user knows the required scope (world, continent, region, capital) and top N.*

### Success End Condition

*The system generates and displays the corresponding reports which includes the columns: Code, Name, Continent, Region, Population, Capital.*

### Failed End Condition

*No report is produced.*

### Primary Actor

*Data Analyst*

### Trigger

*A request for country population reports initiated by WHO internal processes or external stakeholders.*

## MAIN SUCCESS SCENARIO

*1. Data Analyst initiates a request for a country population report.*

*2. Data Analyst captures the scope (which world, continent, region, capital) to get the country population reports*

*3. Data Analyst employee captures Top N to see the most populated countries*

## EXTENSIONS

2. **If scope or parameters are missing/invalid**: System requests correction or notifies that no data is available.

## SUB-VARIATIONS

None

## SCHEDULE

**DUE DATE**: *Release 1.0*

