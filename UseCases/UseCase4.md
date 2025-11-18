# USE CASE: Generate Capital City Reports

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Data Analyst*, I want *all capital cities in the world organized by largest population to smallest* so that *I can analyze global capitals.*

As a *Data Analyst*, I want *all capital cities in a continent organized by largest population to smallest* so that *I can study capitals in that continent.*

As a *Data Analyst*, I want *all capital cities in a region organized by largest population to smallest* so that *I can compare capitals regionally.*

As a *Data Analyst*, I want *the top N populated capital cities in the world where N is provided* so that *I can review the largest global capitals.*

As a *Data Analyst*, I want *the top N populated capital cities in a continent where N is provided* so that *I can analyze major capitals continent-wise.*

As a *Data Analyst*, I want *the top N populated capital cities in a region where N is provided* so that *I can compare major capitals by region.*

### Scope

Covers **world**, **continent**, and **region** levels for all capital cities.

### Level

Primary Task

### Preconditions

The database contains population data for all capital cities along with accurate continent and region classifications.

The Data Analyst knows the required scope (world, continent, or region) and, if applicable, the top N number of cities to be displayed.

### Success End Condition

The system provides an organized report of capital cities sorted by population in descending order, based on the selected scope.

### Failed End Condition

No report is produced due to missing data, incorrect parameters, or invalid scope selection.

### Primary Actor

Data Analyst

### Trigger

A request is initiated by the Data Analyst to generate capital city population reports for global or regional analysis.

---

## MAIN SUCCESS SCENARIO

1. The Data Analyst initiates a request for a capital city population report.
2. The Data Analyst specifies the desired scope (world, continent, or region).
3. The Data Analyst may provide a value for top N to view only the most populated capitals.
4. The system queries the database for the relevant capital city population data.
5. The system sorts and organizes the data in descending order by population.
6. The system generates and displays the capital city report with appropriate columns and values.

---

## EXTENSIONS

**2a.** If scope or parameters are missing/invalid:
- The system prompts the Data Analyst to re-enter valid input or notifies that no matching data is available.

**2b.** If database connection fails:
- The system displays an error message and logs the failure event.

---

## SUB-VARIATIONS

None.

---
 
## SCHEDULE

**DUE DATE:** Release 1.0
