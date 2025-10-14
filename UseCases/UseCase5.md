# USE CASE: General Population Data

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Data Analyst*, I want *the population of the world* so that *I can have a global population summary.*

As a *Data Analyst*, I want *the population of a continent* so that *I can understand demographic scale continentally.*

As a *Data Analyst*, I want *the population of a region* so that *I can analyze population in specific regions.*

As a *Data Analyst*, I want *the population of a country* so that *I can review national population statistics.*

As a *Data Analyst*, I want *the population of a district* so that *I can study smaller administrative areas.*

As a *Data Analyst*, I want *the population of a city* so that *I can understand urban population sizes.*


### Scope

World, Continent, Region, Country, District, City

### Level

Primary task

### Preconditions

The user knows the scope and what to do with it.

Database contains the population data of each city, district, country, etc.

### Success End Condition

The system provides the correct information of the population of the world, a continent, a region, and so on.

### Failed End Condition

The system fails to produce the information.

### Primary Actor

World Health Organization Data Analyst

### Trigger

A request for  general(world/continent/region/etc.) population report initiated by WHO internal processes or external stakeholders.

## MAIN SUCCESS SCENARIO

1. Data analyst starts the database system.
2. System captures the scope and retrieves the relevant population data from the database.
3. System compiles and displays a report for each level.
4. Analyst reviews the report for further use in analysis or comparison.

## EXTENSIONS

2. **If scope or parameters are missing/invalid**:
    1. System requests correction or notifies that no data is available.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0