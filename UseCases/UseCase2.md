# USE CASE: Generate Cities Reports

## CHARACTERISTIC INFORMATION

### Goal in Context

As a Data Analyst, I want all cities in the world organized by largest population to smallest so that I can analyze most populated global cities.

As a Data Analyst, I want all cities in a continent organized by largest population to smallest so that I can study the populations of the cities continentally.

As a Data Analyst, I want all cities in a region organized by largest population to smallest so that I can compare city populations regionally.

As a Data Analyst, I want all cities in a country organized by largest population to smallest so that I can analyze a country’s cities population.

As a Data Analyst, I want all cities in a district organized by largest population to smallest so that I can analyze the population of the cities in that district.

As a Data Analyst, I want the top N populated cities in the world where N is provided so that I can focus on major global cities.

As a Data Analyst, I want the top N populated cities in a continent where N is provided so that I can analyze major cities continent-wide.

As a Data Analyst, I want the top N populated cities in a region where N is provided so that I can focus on key cities in that region.

As a Data Analyst, I want the top N populated cities in a country where N is provided so that I can analyze the largest cities nationally.

As a Data Analyst, I want the top N populated cities in a district where N is provided so that I can review key cities locally.
### Scope

World, continent, region, country, district

### Level

Primary task

### Preconditions

The database contains the population data of each city and correct geographic classification.

The user knows the required scope (world, continent, region, country, district) and top N.

### Success End Condition

The system generates and displays the corresponding reports which includes the columns: Name, Country, District, Population.

### Failed End Condition

No report is produced.

### Primary Actor

Data Analyst

### Trigger

Request for city population data to support planning or analysis.

## MAIN SUCCESS SCENARIO

1. Data Analyst requests a city population report.

2. Data Analyst captures the scope (which continent, country, region, district) to get the city population reports

3. WHO employee captures Top N to see the most populated cities

4. System retrieves relevant city population data from the database.



## EXTENSIONS

2. **If scope or parameters are missing/invalid**: System requests valid parameter.

3. **No cities found**:  system notifies analyst, no report.

## SUB-VARIATIONS

None

## SCHEDULE

**DUE DATE**: Release 1.0

