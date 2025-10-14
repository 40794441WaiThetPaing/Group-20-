# USE CASE: Generate Cities Reports

## CHARACTERISTIC INFORMATION

### Goal in Context

*As a WHO employee, I want to see all the cities in the world sorted by population so that I can know which cities have the most people.*

*As a WHO employee, I want to see all the cities in a continent sorted by population so that I can study urban population in that area.*

*As a WHO employee, I want to see all the cities in a region sorted by population so that I can compare city populations across regions.*

*As a WHO employee, I want to see all the cities in a country sorted by population so that I can understand the largest cities in that country.*

*As a WHO employee, I want to see all the cities in a district sorted by population so that I can study smaller areas.*

*As a WHO employee, I want to view the top N cities in the world so that I can focus on the biggest global cities.*

*As a WHO employee, I want to view the top N cities in a continent so that I can analyze large cities in that continent.*

*As a WHO employee, I want to view the top N cities in a region so that I can compare the main cities in that region.*

*As a WHO employee, I want to view the top N cities in a country so that I can focus on the largest cities there.*

*As a WHO employee, I want to view the top N cities in a district so that I can focus on the biggest cities in that district.*
### Scope

*World, continent, region, country, district*

### Level

*Primary task*

### Preconditions

*The database contains the population data of each city and correct geographic classification.*

*The user knows the required scope (world, continent, region, country, district) and top N.*

### Success End Condition

*The system generates and displays the corresponding reports which includes the columns: Name, Country, District, Population.*

### Failed End Condition

*No report is produced.*

### Primary Actor

*World Health Organization Employee*

### Trigger

*A request for city population reports initiated by WHO internal processes or external stakeholders.*

## MAIN SUCCESS SCENARIO

*1. WHO employee initiates a request for a city population report.*

*2. WHO employee captures the scope (which continent, country, region, district) to get the city population reports*

*3. WHO employee captures Top N to see the most populated cities*

## EXTENSIONS

2. **If scope or parameters are missing/invalid**: System requests correction or notifies that no data is available.

## SUB-VARIATIONS

None

## SCHEDULE

**DUE DATE**: *Release 1.0*

