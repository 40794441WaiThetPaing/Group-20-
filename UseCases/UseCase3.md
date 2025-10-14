# USE CASE: Generate Population Reports

## CHARACTERISTIC INFORMATION

### Goal in Context

*As a Data Analyst, I want the population of people living in cities and not living in cities in each continent so that I can understand urbanization trends continent-wide.*

*As a Data Analyst, I want the population of people living in cities and not living in cities in each region so that I can study urban and rural distribution regionally.*

*As a Data Analyst, I want the population of people living in cities and not living in cities in each country so that I can analyze urban/rural splits nationally.*

*As a Data Analyst, I want to know the number of people who speak Chinese, English, Hindi, Spanish, and Arabic globally, including the percentage of the world population, so that I can report on language demographics.*

### Scope

*World, continent, region, country, district*

### Level

*Primary task*

### Preconditions

*The database contains accurate and up to date population data for each country, region and continent including details of cities and non urban populations. The user can access to the reporting system and can specify the desired reports.*

### Success End Condition

*The system generates and displays population reports showing total population, urban population and non urban population for the selected scope.*

### Failed End Condition

*No report is produced or incomplete data is displayed.*

### Primary Actor

*World Health Organization Employee*

### Trigger

*A request for population reports initiated by WHO internal analysis or external stakeholders.*

### MAIN SUCCESS SCENARIO

*WHO employee initiates a request for a population report.*

*WHO employee specifies the scope.*

*The system retrieves total, urban and non urban population data from the database.*

*The system generates and displays the report with the requested population breakdown.*

### EXTENSIONS

*If scope or population data is missing or invalid: System requests correction or notifies the user that no data is available.*

### SUB-VARIATIONS

*None*

### SCHEDULE

*DUE DATE: Release 1.0*
