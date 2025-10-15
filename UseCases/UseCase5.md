# USE CASE: General Population Data

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Data Analyst*, I want *to access population data (world, continent, region, country, district, city)* so that *I can create reports, perform comparisons, and conduct analysis.*

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